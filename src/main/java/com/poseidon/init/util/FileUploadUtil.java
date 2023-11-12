package com.poseidon.init.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileUploadUtil {
    public static void saveFile(final String uploadLocation, final String fileName, final MultipartFile file)
            throws IOException {
        var uploadPath = Paths.get(uploadLocation);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            var fileLocation = uploadPath.resolve(fileName);
            Files.copy(inputStream, fileLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException("Unable to save the file : " + fileName, ex);
        }
    }

    public static void cleanDir(final String dir) {
        var dirPath = Paths.get(dir);
        try (Stream<Path> stream = Files.list(dirPath)) {
            stream.forEach(FileUploadUtil::deleteFiles);
        } catch (IOException exception) {
            System.out.println("Could not list directory : " + dirPath);
        }
    }

    private static void deleteFiles(final Path file) {
        if (!Files.isDirectory(file)) {
            try {
                Files.delete(file);
            } catch (IOException ex) {
                System.out.println("Could not delete file : " + file);
            }
        }
    }
}
