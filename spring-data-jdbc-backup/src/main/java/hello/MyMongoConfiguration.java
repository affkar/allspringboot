/*
package hello;

import com.mongodb.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;
import java.util.List;

@Configuration
public class MyMongoConfiguration extends AbstractMongoConfiguration {
    @Bean
    @Override
    public MongoClient mongoClient() {

        MongoClientURI uri=new MongoClientURI("mongodb://localhost:27017/test?retryWrites=true");
        return new MongoClient(uri);
        //return new MongoClient(Collections.singletonList(new ServerAddress("cluster0-bii6g.gcp.mongodb.net",27017)),Collections.singletonList(MongoCredential.createCredential("karthick","test","table92$".toCharArray())));
        //return MongoClients.create("cluster0-bii6g.gcp.mongodb.net");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient){
        MongoTemplate mongoTemplate=new MongoTemplate(mongoClient,"test");
        return mongoTemplate;
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }
}
*/
