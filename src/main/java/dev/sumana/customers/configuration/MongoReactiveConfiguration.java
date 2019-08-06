package dev.sumana.customers.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@EnableReactiveMongoRepositories
public class MongoReactiveConfiguration extends AbstractReactiveMongoConfiguration {

    @Override
    public MongoClient reactiveMongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToSocketSettings(b -> b.connectTimeout(60, TimeUnit.SECONDS))
                .applyToConnectionPoolSettings(cps -> cps.maxConnectionLifeTime(Long.MAX_VALUE, TimeUnit.SECONDS))
                .build();
        return MongoClients.create(settings);
    }

    @Override
    protected String getDatabaseName() {
        return null;
    }

}
