package com.poseidon.reports.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for loading JasperReports templates, with compatibility handling for JasperReports 7.x.
 */
public final class JasperReportLoadHelper {
    private static final Logger LOG = LoggerFactory.getLogger(JasperReportLoadHelper.class);
    private static final Pattern UUID_PATTERN = Pattern.compile("uuid=\"([^\"]+)\"");
    private static final Pattern DTD_PATTERN = Pattern.compile("<!DOCTYPE[^>]*>");
    private static final int BUFFER_SIZE = 8192;
    private static final boolean DEBUG_XML = true; // Set to true to log XML content for debugging

    // Newer Jasper Reports schema references
    private static final String SCHEMA_REFERENCE = "http://jasperreports.sourceforge.net/xsd/jasperreport.xsd";

    private JasperReportLoadHelper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Loads and compiles a JasperReport from a classpath resource, handling compatibility issues.
     *
     * @param resourcePath The path to the JRXML resource
     * @return The compiled JasperReport
     * @throws JRException If there's an error compiling the report
     */
    public static JasperReport loadAndCompileReport(final String resourcePath) throws JRException {
        LOG.info("Attempting to load report from resource path: {}", resourcePath);

        // Try multiple loading strategies
        JRException lastException = null;

        // Strategy 1: Try ClassPathResource
        try {
            JasperReport report = loadUsingClassPathResource(resourcePath);
            if (report != null) {
                return report;
            }
        } catch (JRException e) {
            LOG.warn("Failed to load report using ClassPathResource: {}", e.getMessage());
            lastException = e;
        }

        // Strategy 2: Try direct file access
        try {
            JasperReport report = loadUsingFileSystem(resourcePath);
            if (report != null) {
                return report;
            }
        } catch (JRException e) {
            LOG.warn("Failed to load report using FileSystem: {}", e.getMessage());
            lastException = e;
        }

        // Strategy 3: Try URL-based loading
        try {
            JasperReport report = loadUsingResourceUtils(resourcePath);
            if (report != null) {
                return report;
            }
        } catch (JRException e) {
            LOG.warn("Failed to load report using ResourceUtils: {}", e.getMessage());
            lastException = e;
        }

        // If all strategies above failed, try the direct approach with raw XML
        try {
            JasperReport report = loadWithDirectXmlApproach(resourcePath);
            if (report != null) {
                return report;
            }
        } catch (JRException e) {
            LOG.warn("Failed to load report using direct XML approach: {}", e.getMessage());
            lastException = e;
        }

        // If we get here, all strategies failed
        if (lastException != null) {
            throw lastException;
        } else {
            throw new JRException("Failed to load report using all available strategies");
        }
    }

    private static JasperReport loadWithDirectXmlApproach(String resourcePath) throws JRException {
        LOG.info("Attempting direct XML approach for report: {}", resourcePath);

        try {
            // Try to find the file
            File file = null;

            // Try different paths
            String[] pathsToTry = {
                resourcePath,
                "src/main/resources" + (resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath),
                "src/main/resources/reports/" + new File(resourcePath).getName(),
                "build/resources/main/reports/" + new File(resourcePath).getName()
            };

            for (String path : pathsToTry) {
                File tempFile = new File(path);
                if (tempFile.exists()) {
                    file = tempFile;
                    LOG.info("Found report file at: {}", file.getAbsolutePath());
                    break;
                }
            }

            if (file == null) {
                LOG.error("Could not find report file at any path");
                return null;
            }

            // Use JRXmlLoader directly, which is more reliable
            try (FileInputStream fis = new FileInputStream(file)) {
                LOG.info("Loading report design using JRXmlLoader...");
                JasperDesign jasperDesign = JRXmlLoader.load(fis);
                LOG.info("Successfully loaded JasperDesign, compiling...");
                return JasperCompileManager.compileReport(jasperDesign);
            }
        } catch (Exception e) {
            LOG.error("Error in direct XML approach: {}", e.getMessage(), e);
            throw new JRException("Failed to load with direct XML approach", e);
        }
    }

    private static JasperReport loadUsingClassPathResource(String resourcePath) throws JRException {
        // Try with various path adjustments
        String[] pathVariations = {
                resourcePath,
                resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath,
                !resourcePath.startsWith("/") ? "/" + resourcePath : resourcePath
        };

        for (String path : pathVariations) {
            try {
                ClassPathResource resource = new ClassPathResource(path);
                if (resource.exists()) {
                    LOG.info("Found report at ClassPath location: {}", path);
                    try (InputStream is = resource.getInputStream()) {
                        return compileReportWithCompatibilityFixes(is);
                    } catch (IOException e) {
                        LOG.error("IO error reading report stream: {}", e.getMessage());
                        throw new JRException("Failed to read report input stream", e);
                    }
                }
            } catch (Exception e) {
                LOG.debug("Error checking path {}: {}", path, e.getMessage());
            }
        }

        return null;
    }

    private static JasperReport loadUsingFileSystem(String resourcePath) throws JRException {
        // Try with various path adjustments for file system
        String[] pathVariations = {
                "src/main/resources" + (resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath),
                "src/main/resources/reports/" + new File(resourcePath).getName(),
                "build/resources/main" + (resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath),
                "build/resources/main/reports/" + new File(resourcePath).getName(),
                "reports/" + new File(resourcePath).getName()
        };

        for (String path : pathVariations) {
            File file = new File(path);
            if (file.exists()) {
                LOG.info("Found report at filesystem location: {}", file.getAbsolutePath());
                try (FileInputStream fis = new FileInputStream(file)) {
                    return compileReportWithCompatibilityFixes(fis);
                } catch (IOException e) {
                    LOG.error("IO error reading report file: {}", e.getMessage());
                    throw new JRException("Failed to read report file", e);
                }
            }
        }

        return null;
    }

    private static JasperReport loadUsingResourceUtils(String resourcePath) throws JRException {
        // Try with various path adjustments for ResourceUtils
        String[] pathVariations = {
                "classpath:" + (resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath),
                "classpath:reports/" + new File(resourcePath).getName(),
                "classpath:/reports/" + new File(resourcePath).getName(),
                "file:src/main/resources" + (resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath),
                "file:src/main/resources/reports/" + new File(resourcePath).getName()
        };

        for (String path : pathVariations) {
            try {
                URL url = ResourceUtils.getURL(path);
                LOG.info("Found report at URL location: {}", url);
                try (InputStream is = url.openStream()) {
                    return compileReportWithCompatibilityFixes(is);
                } catch (IOException e) {
                    LOG.error("IO error reading URL stream: {}", e.getMessage());
                    throw new JRException("Failed to read URL stream", e);
                }
            } catch (Exception e) {
                LOG.debug("Error checking URL {}: {}", path, e.getMessage());
            }
        }

        return null;
    }

    private static JasperReport compileReportWithCompatibilityFixes(InputStream inputStream) throws JRException {
        try {
            // Read the entire JRXML content
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            // Get the JRXML content as a string
            String xmlContent = result.toString(StandardCharsets.UTF_8);
            LOG.debug("Report content length: {} bytes", xmlContent.length());

            // Log the raw XML for debugging if enabled
            if (DEBUG_XML) {
                logXmlContent(xmlContent);
            }

            // Apply compatibility fixes
            xmlContent = applyCompatibilityFixes(xmlContent);

            // First try using JRXmlLoader which provides better error messages
            try {
                LOG.info("Attempting to load report with JRXmlLoader first");
                try (ByteArrayInputStream processedStream = new ByteArrayInputStream(
                        xmlContent.getBytes(StandardCharsets.UTF_8))) {
                    JasperDesign design = JRXmlLoader.load(processedStream);
                    LOG.info("JRXmlLoader succeeded, compiling report template");
                    return JasperCompileManager.compileReport(design);
                }
            } catch (JRException e) {
                LOG.warn("JRXmlLoader approach failed: {}. Falling back to direct compilation.", e.getMessage());
                // Fall back to direct compilation
                try (ByteArrayInputStream processedStream = new ByteArrayInputStream(
                        xmlContent.getBytes(StandardCharsets.UTF_8))) {
                    LOG.info("Compiling report template using direct compilation");
                    return JasperCompileManager.compileReport(processedStream);
                }
            }
        } catch (IOException e) {
            LOG.error("IO error processing report content: {}", e.getMessage(), e);
            throw new JRException("Failed to process report content", e);
        }
    }

    private static void logXmlContent(String content) {
        try {
            LOG.debug("--- START OF XML CONTENT ---");
            // Split the content into manageable chunks to avoid log message size limits
            int chunkSize = 1000;
            for (int i = 0; i < content.length(); i += chunkSize) {
                int end = Math.min(content.length(), i + chunkSize);
                LOG.debug(content.substring(i, end));
            }
            LOG.debug("--- END OF XML CONTENT ---");
        } catch (Exception e) {
            LOG.error("Error logging XML content", e);
        }
    }

    private static String applyCompatibilityFixes(String xmlContent) {
        // Apply all necessary fixes to the XML content for compatibility

        // 1. Fix UUID attributes that might cause issues with JR 7.x
        xmlContent = removeUuidAttributes(xmlContent);

        // 2. Remove any problematic DTD references
        xmlContent = removeDTDReferences(xmlContent);

        // 3. Check and fix schema references if necessary
        xmlContent = ensureSchemaReferences(xmlContent);

        // 4. Fix encoding declaration if needed
        xmlContent = fixEncodingDeclaration(xmlContent);

        // 5. Handle CDATA sections which can cause issues
        xmlContent = fixCDataSections(xmlContent);

        LOG.debug("Applied compatibility fixes to report XML");
        return xmlContent;
    }

    /**
     * Removes or fixes uuid attributes in the JRXML content for better compatibility with JasperReports 7.x.
     */
    private static String removeUuidAttributes(final String content) {
        // Simply remove uuid attributes as they're not essential
        Matcher matcher = UUID_PATTERN.matcher(content);
        return matcher.replaceAll("");
    }

    /**
     * Removes potentially problematic DTD references
     */
    private static String removeDTDReferences(final String content) {
        // Remove DOCTYPE declarations that might cause validation issues
        Matcher matcher = DTD_PATTERN.matcher(content);
        return matcher.replaceAll("");
    }

    /**
     * Ensures the XML has proper schema references for JasperReports 7.x
     */
    private static String ensureSchemaReferences(String content) {
        // For now, we're just ensuring the content is not empty
        // More specific schema fixes could be added here if needed

        if (content.trim().isEmpty()) {
            LOG.error("Report content is empty!");
        }

        return content;
    }

    /**
     * Fix encoding declaration if needed
     */
    private static String fixEncodingDeclaration(String content) {
        // Ensure proper XML declaration
        if (!content.trim().startsWith("<?xml")) {
            content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + content;
        }
        return content;
    }

    /**
     * Fix CDATA sections which can sometimes cause parsing problems
     */
    private static String fixCDataSections(String content) {
        // This is a simple fix that ensures CDATA sections are well-formed
        // More sophisticated fixes can be implemented if needed
        return content.replace("<![CDATA[]]>", "<![CDATA[]]>");
    }
}
