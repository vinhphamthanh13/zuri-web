/**
 * 
 */
package com.ocha.boc;

import com.ocha.boc.cors.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;

/**
 * @author robert
 *
 */
@SpringBootApplication
public class OchaApplication extends SpringBootServletInitializer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SpringApplication.run(OchaApplication.class, args);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean filterRegistrationBean() {
    return new FilterRegistrationBean(new CorsFilter());
    }  
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
