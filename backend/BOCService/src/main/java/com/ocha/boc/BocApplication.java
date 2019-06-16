/**
 * 
 */
package com.ocha.boc;

import com.ocha.boc.cors.CorsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * @author robert
 *
 */
@SpringBootApplication
public class BocApplication extends SpringBootServletInitializer {

	@Value("${aws.region}")
	String awsRegion;
	
	@Value("${aws.access.id}")
	String awsAccessId;
	
	@Value("${aws.secret}")
	String awsSecret;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BocApplication.class, args);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean filterRegistrationBean() {
    return new FilterRegistrationBean(new CorsFilter());
    }  
	@Bean
    public AmazonS3 getAmazonS3(){
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
		   		  .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessId, awsSecret)))
		             .withRegion(Regions.fromName(awsRegion))
		             .build();	
	      return s3Client;
	 }
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
