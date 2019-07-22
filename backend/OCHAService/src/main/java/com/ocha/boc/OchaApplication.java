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

    @Autowired
    private BangGiaRepository bangGiaRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;

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

    @Value(value = "${boc.table.danhmuc}")
    private String danhMucTableName;

    @Value(value = "${boc.table.khuyenmai}")
    private String khuyenMaiTableName;

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
        initBangGia(db);
        initDanhMuc(db);
        initMatHangTable(db);
        initKhuyenMai(db);
        initNguyenLieuTable(db);
        initOrder(db);
    }

    private MongoClient connectMongoDB() {
        MongoClient mongo = new MongoClient(mongoDBHostName, mongoDBPortNumber);
        return mongo;
    }

    private void initBangGiaDetail(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, bangGiaDetailTableName);
            List<BangGiaDetail> bangGiaDetails = new ArrayList<BangGiaDetail>();
            InputStream stream = OchaApplication.class.getResourceAsStream("/banggia_detail.json");
            ObjectMapper mapper = new ObjectMapper();
            bangGiaDetails = mapper.readValue(stream, new TypeReference<List<BangGiaDetail>>() {
            });
            if (!isExisted) {
                bangGiaDetailRepository.saveAll(bangGiaDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMatHangTable(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, matHangTableName);
            List<MatHang> listMatHang = new ArrayList<MatHang>();
            InputStream stream = OchaApplication.class.getResourceAsStream("/mathang.json");
            ObjectMapper mapper = new ObjectMapper();
            listMatHang = mapper.readValue(stream, new TypeReference<List<MatHang>>() {
            });
            if (!isExisted) {
                matHangRepository.saveAll(listMatHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initNguyenLieuTable(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, nguyenLieuTableName);
            List<NguyenLieu> listNguyenLieu = new ArrayList<NguyenLieu>();
            InputStream stream = OchaApplication.class.getResourceAsStream("/nguyenlieu.json");
            ObjectMapper mapper = new ObjectMapper();
            listNguyenLieu = mapper.readValue(stream, new TypeReference<List<NguyenLieu>>() {
            });
            if (!isExisted) {
                nguyenLieuRepository.saveAll(listNguyenLieu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOrder(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, orderTableName);
            List<Order> orders = new ArrayList<Order>();
            InputStream stream = OchaApplication.class.getResourceAsStream("/order.json");
            ObjectMapper mapper = new ObjectMapper();
            orders = mapper.readValue(stream, new TypeReference<List<Order>>() {
            });
            if (!isExisted) {
                orderRepository.saveAll(orders);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBangGia(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, bangGiaTableName);
            List<BangGia> bangGias = new ArrayList<BangGia>();
            InputStream stream = OchaApplication.class.getResourceAsStream("/banggia.json");
            ObjectMapper mapper = new ObjectMapper();
            bangGias = mapper.readValue(stream, new TypeReference<List<BangGia>>() {
            });
            if (!isExisted) {
                bangGiaRepository.saveAll(bangGias);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDanhMuc(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, danhMucTableName);
            List<DanhMuc> danhMucList = new ArrayList<DanhMuc>();
            InputStream stream = OchaApplication.class.getResourceAsStream("/danhmuc.json");
            ObjectMapper mapper = new ObjectMapper();
            danhMucList = mapper.readValue(stream, new TypeReference<List<DanhMuc>>() {
            });
            if (!isExisted) {
                danhMucRepository.saveAll(danhMucList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initKhuyenMai(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, khuyenMaiTableName);
            List<KhuyenMai> khuyenMaiList = new ArrayList<KhuyenMai>();
            InputStream stream = OchaApplication.class.getResourceAsStream("/khuyenmai.json");
            ObjectMapper mapper = new ObjectMapper();
            khuyenMaiList = mapper.readValue(stream, new TypeReference<List<KhuyenMai>>() {
            });
            if (!isExisted) {
                khuyenMaiRepository.saveAll(khuyenMaiList);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
