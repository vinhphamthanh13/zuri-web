package com.ocha.boc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ocha.boc.entity.DanhMucSanPham;
import com.ocha.boc.entity.MoHinhKinhDoanh;
import com.ocha.boc.repository.DanhMucSanPhamRepository;
import com.ocha.boc.repository.MoHinhKinhDoanhRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.List;


@SpringBootApplication(
        exclude = {RedisRepositoriesAutoConfiguration.class}
)
@EnableCaching
@Slf4j
public class OchaApplication {

    private static final String MO_HINH_KINH_DOANH_JSON = "/mohinhkinhdoanh.json";

    private static final String DANH_MUC_SAN_PHAM_JSON = "/danhmucsanpham.json";

    @Autowired
    private MoHinhKinhDoanhRepository moHinhKinhDoanhRepository;

    @Autowired
    private DanhMucSanPhamRepository danhMucSanPhamRepository;

    @Value(value = "${boc.table.danhmucsanpham}")
    private String danhMucSanPhamTableName;

    @Value(value = "${boc.table.mohinhkinhdoanh}")
    private String moHinhKinhDoanhTableName;

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


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void migrateData() {
        MongoClient mongo = connectMongoDB();
        MongoDatabase db = mongo.getDatabase(databaseName);
        initMoHinhKinhDoanh(db);
        initDanhMucSanPham(db);
    }

    private MongoClient connectMongoDB() {
        MongoClient mongo = new MongoClient(mongoDBHostName, mongoDBPortNumber);
        return mongo;
    }

    private void initMoHinhKinhDoanh(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, moHinhKinhDoanhTableName);
            if (!isExisted) {
                List<MoHinhKinhDoanh> moHinhKinhDoanhList;

                InputStream stream = OchaApplication.class.getResourceAsStream(MO_HINH_KINH_DOANH_JSON);
                ObjectMapper mapper = new ObjectMapper();
                moHinhKinhDoanhList = mapper.readValue(stream, new TypeReference<List<MoHinhKinhDoanh>>() {

                });
                if (moHinhKinhDoanhList != null && !moHinhKinhDoanhList.isEmpty()) {
                    moHinhKinhDoanhRepository.deleteAll();
                    moHinhKinhDoanhRepository.saveAll(moHinhKinhDoanhList);
                }

            }
        } catch (Exception e) {
            log.error("Exception while init Mo Hinh Kinh Doanh: ", e);
        }
    }

    private void initDanhMucSanPham(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, danhMucSanPhamTableName);
            if (!isExisted) {
                List<DanhMucSanPham> danhMucSanPhamList;

                InputStream stream = OchaApplication.class.getResourceAsStream(DANH_MUC_SAN_PHAM_JSON);
                ObjectMapper mapper = new ObjectMapper();
                danhMucSanPhamList = mapper.readValue(stream, new TypeReference<List<DanhMucSanPham>>() {

                });
                if (danhMucSanPhamList != null && !danhMucSanPhamList.isEmpty()) {
                    danhMucSanPhamRepository.deleteAll();
                    danhMucSanPhamRepository.saveAll(danhMucSanPhamList);
                }

            }
        } catch (Exception e) {
            log.error("Exception while init Danh Muc San Pham: ", e);
        }
    }

    private boolean checkExistsCollectionName(MongoDatabase db, String collectionName) {
        MongoCollection dbCollection = db.getCollection(collectionName);
        return dbCollection.count() > 0;
    }

}
