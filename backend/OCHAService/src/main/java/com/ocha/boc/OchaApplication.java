package com.ocha.boc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ocha.boc.entity.BusinessModelsType;
import com.ocha.boc.entity.ProductPortfolio;
import com.ocha.boc.repository.BusinessModelsTypeRepository;
import com.ocha.boc.repository.ProductPortfolioRepository;
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
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.List;


@SpringBootApplication(
        exclude = {RedisRepositoriesAutoConfiguration.class}
)
@EnableCaching
@Slf4j
public class OchaApplication {

    private static final String BUSINESS_MODELS_TYPE = "/businessModelsType.json";

    private static final String PRODUCT_PORTFOLIO = "/productPortfolio.json";

    @Autowired
    private BusinessModelsTypeRepository businessModelsTypeRepository;

    @Autowired
    private ProductPortfolioRepository productPortfolioRepository;

    @Value(value = "${boc.table.product.portfolio}")
    private String productPortfolioTableName;

    @Value(value = "${boc.table.business.models.type}")
    private String businessModelsTypeTableName;

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
        initBusinessModels(db);
        initProductPortfolio(db);
    }

    private MongoClient connectMongoDB() {
        MongoClient mongo = new MongoClient(mongoDBHostName, mongoDBPortNumber);
        return mongo;
    }

    private void initBusinessModels(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, businessModelsTypeTableName);
            if (!isExisted) {
                List<BusinessModelsType> businessModelsTypeList;

                InputStream stream = OchaApplication.class.getResourceAsStream(BUSINESS_MODELS_TYPE);
                ObjectMapper mapper = new ObjectMapper();
                businessModelsTypeList = mapper.readValue(stream, new TypeReference<List<BusinessModelsType>>() {

                });
                if (businessModelsTypeList != null && !businessModelsTypeList.isEmpty()) {
                    businessModelsTypeRepository.deleteAll();
                    businessModelsTypeRepository.saveAll(businessModelsTypeList);
                }

            }
        } catch (Exception e) {
            log.error("Exception while init Business Models: ", e);
        }
    }

    private void initProductPortfolio(MongoDatabase db) {
        try {
            boolean isExisted = checkExistsCollectionName(db, productPortfolioTableName);
            if (!isExisted) {
                List<ProductPortfolio> productPortfolioList;

                InputStream stream = OchaApplication.class.getResourceAsStream(PRODUCT_PORTFOLIO);
                ObjectMapper mapper = new ObjectMapper();
                productPortfolioList = mapper.readValue(stream, new TypeReference<List<ProductPortfolio>>() {

                });
                if (productPortfolioList != null && !productPortfolioList.isEmpty()) {
                    productPortfolioRepository.deleteAll();
                    productPortfolioRepository.saveAll(productPortfolioList);
                }

            }
        } catch (Exception e) {
            log.error("Exception while init Product Portfolio: ", e);
        }
    }

    private boolean checkExistsCollectionName(MongoDatabase db, String collectionName) {
        MongoCollection dbCollection = db.getCollection(collectionName);
        return dbCollection.count() > 0;
    }

}
