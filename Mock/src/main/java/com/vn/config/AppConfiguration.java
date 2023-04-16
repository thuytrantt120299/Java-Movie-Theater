package com.vn.config;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;

@Getter
@Configuration
public class AppConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");

		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");

		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/image/");

		

	    registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/image/")
	            .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());


		 exposeDirectory("account-images", registry);

		
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
	 private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
	        Path uploadDir = Paths.get(dirName);
	        String uploadPath = uploadDir.toFile().getAbsolutePath();
	         
	        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
	         
	        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
	    }


	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasenames(new String[] { "classpath:i18n/message" });
		source.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return source;
	}

}
