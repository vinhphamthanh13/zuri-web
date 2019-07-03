/**
 *
 */
package com.ocha.boc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ocha.boc.cors.CorsFilter;
import com.ocha.boc.entity.*;
import com.ocha.boc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatHangRepository matHangRepository;

    @Autowired
    private NguyenLieuRepository nguyenLieuRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value(value = "${boc.table.banggia}")
    private String bangGiaTableName;

    @Value(value = "${boc.table.mathang}")
    private String matHangTableName;

    @Value(value = "${boc.table.user}")
    private String userTableName;

    @Value(value = "${boc.table.nguyenlieu}")
    private String nguyenLieuTableName;

    @Value(value = "${boc.table.banggiaDetail}")
    private String bangGiaDetailTableName;

    @Value(value = "${boc.table.order}")
    private String orderTableName;

    @Value(value = "${spring.data.mongodb.host}")
    private String mongoDBHostName;

    @Value(value = "${spring.data.mongodb.port}")
    private int mongoDBPortNumber;


    @Value(value = "${spring.data.mongodb.database}")
    private String databaseName;

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
        MongoClient mongo = connectMongoDB();
        MongoDatabase db = mongo.getDatabase(databaseName);
        initBangGiaDetail(db);
        initUserTable(db);
        initMatHangTable(db);
        initNguyenLieuTable(db);
        initOrder(db);
    }

    public MongoClient connectMongoDB() {
        MongoClient mongo = new MongoClient(mongoDBHostName, mongoDBPortNumber);
        return mongo;
    }

    public void initBangGiaDetail(MongoDatabase db) {
        boolean isExisted = checkExistsCollectionName(db, bangGiaDetailTableName);
        if (!isExisted) {
            List<BangGiaDetail> bangGiaDetails = new ArrayList<>();
            try {
                InputStream stream = OchaApplication.class.getResourceAsStream("/banggia_detail.json");
                ObjectMapper mapper = new ObjectMapper();
                bangGiaDetails = mapper.readValue(stream, new TypeReference<List<BangGiaDetail>>() {
                });
                //store to db
                bangGiaDetailRepository.deleteAll();
                bangGiaDetailRepository.saveAll(bangGiaDetails);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initUserTable(MongoDatabase db) {
        boolean isExisted = checkExistsCollectionName(db, userTableName);
        if (!isExisted) {
            List<User> users = new ArrayList<User>();
            try {
                InputStream stream = OchaApplication.class.getResourceAsStream("/user.json");
                ObjectMapper mapper = new ObjectMapper();
                users = mapper.readValue(stream, new TypeReference<List<User>>() {
                });
                //store to db
                userRepository.deleteAll();
                userRepository.saveAll(users);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initMatHangTable(MongoDatabase db) {
        boolean isExisted = checkExistsCollectionName(db, matHangTableName);
        if (!isExisted) {
            List<MatHang> listMatHang = new ArrayList<MatHang>();
            try {
                InputStream stream = OchaApplication.class.getResourceAsStream("/mathang.json");
                ObjectMapper mapper = new ObjectMapper();
                listMatHang = mapper.readValue(stream, new TypeReference<List<MatHang>>() {
                });
                //store to db
                matHangRepository.deleteAll();
                matHangRepository.saveAll(listMatHang);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initNguyenLieuTable(MongoDatabase db) {
        boolean isExisted = checkExistsCollectionName(db, nguyenLieuTableName);
        if (!isExisted) {
            List<NguyenLieu> listNguyenLieu = new ArrayList<NguyenLieu>();
            try {
                InputStream stream = OchaApplication.class.getResourceAsStream("/nguyenlieu.json");
                ObjectMapper mapper = new ObjectMapper();
                listNguyenLieu = mapper.readValue(stream, new TypeReference<List<NguyenLieu>>() {
                });
                //store to db
                nguyenLieuRepository.deleteAll();
                nguyenLieuRepository.saveAll(listNguyenLieu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initOrder(MongoDatabase db) {
        boolean isExisted = checkExistsCollectionName(db, orderTableName);
        if (!isExisted) {
            List<Order> orders = new ArrayList<Order>();
            try {
                InputStream stream = OchaApplication.class.getResourceAsStream("/order.json");
                ObjectMapper mapper = new ObjectMapper();
                orders = mapper.readValue(stream, new TypeReference<List<Order>>() {
                });
                //store to db
                orderRepository.deleteAll();
                orderRepository.saveAll(orders);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkExistsCollectionName(MongoDatabase db, String collectionName) {
        MongoCollection dbCollection = db.getCollection(collectionName);
        if (dbCollection.count() > 0) {
            return true;
        }
        return false;
    }


}
