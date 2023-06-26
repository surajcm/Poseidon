package com.poseidon.init;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        var userPhotoLoc = "user-photos";
        var userPhotoDirectory = Paths.get(userPhotoLoc);
        var userPhotosLocation = userPhotoDirectory.toFile().getAbsoluteFile();
        registry.addResourceHandler("/" + userPhotoLoc + "/**")
                .addResourceLocations("file:/" + userPhotosLocation + "/");
    }
}
