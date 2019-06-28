/**
 *
 */
package com.ocha.boc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocha.boc.cors.CorsFilter;
import com.ocha.boc.entity.BangGiaDetail;
import com.ocha.boc.repository.BangGiaDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author robert
 *
 */
@SpringBootApplication
public class OchaApplication extends SpringBootServletInitializer {

    @Autowired
    private BangGiaDetailRepository bangGiaDetailRepository;

    /**
     * @param args
     */
    public static void main(String[] args) {

        SpringApplication.run(OchaApplication.class, args);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        return new FilterRegistrationBean(new CorsFilter());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void migrateMasterAddress() {
        initBangGiaDetail();
    }

    private void initBangGiaDetail() {
        List<BangGiaDetail> bangGiaDetails = new ArrayList<>();
        try {
            InputStream stream = OchaApplication.class.getResourceAsStream("/banggia_detail.json");
            ObjectMapper mapper = new ObjectMapper();
            bangGiaDetails = mapper.readValue(stream, new TypeReference<List<BangGiaDetail>>() {
            });

            //store to db
            if (bangGiaDetails != null && !bangGiaDetails.isEmpty()) {
                //try to delete all already record first
                bangGiaDetailRepository.deleteAll();
                //stored the new records
                bangGiaDetailRepository.saveAll(bangGiaDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
