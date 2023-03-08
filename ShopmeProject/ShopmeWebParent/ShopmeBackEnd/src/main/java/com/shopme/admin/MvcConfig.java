package com.shopme.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shopme.admin.paging.PagingAndSortingArgumentResolver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String dirName = "user-photos";
//        Path userPhotosDir = Paths.get(dirName);
//
//        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
//
//        // macos/unix ("file:" + userPhotosPath + "/") NOT ("file:/" + userPhotosPath + "/")
//
//        registry.addResourceHandler("/" + dirName + "/**")
//                .addResourceLocations("file:" + userPhotosPath + "/");
//
//
//        // 现在working directory是 /Users/juncenli/Java/Project/EcommerceApp/ShopmeProject/ShopmeWebParent/ShopmeBackEnd
//        // 所以我们需要往前一下找到category的path
//        String categoryImagesDirName = "../category-images";
//        Path categoryImagesDir = Paths.get(categoryImagesDirName);
//
//        String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();
//
//        registry.addResourceHandler("/category-images/**")
//                .addResourceLocations("file:" + categoryImagesPath + "/");
//
//        String brandLogosDirName = "../brand-logos";
//        Path brandLogosDir = Paths.get(brandLogosDirName);
//
//        String brandLogosPath = brandLogosDir.toFile().getAbsolutePath();
//
//        registry.addResourceHandler("/brand-logos/**")
//                .addResourceLocations("file:" + brandLogosPath + "/");
        exposeDirectory("user-photos", registry);
        exposeDirectory("../category-images", registry);
        exposeDirectory("../brand-logos", registry);
        exposeDirectory("../product-images", registry);
        exposeDirectory("../site-logo", registry);
    }

    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
        Path path = Paths.get(pathPattern);
        String absolutePath = path.toFile().getAbsolutePath();

        // 如果有 .. 则需要把..去除， 所以使用了replace method
        String logicalPath = pathPattern.replace("../", "") + "/**";

        registry.addResourceHandler(logicalPath)
                .addResourceLocations("file:" + absolutePath + "/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PagingAndSortingArgumentResolver());
    }
}