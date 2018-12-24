package hello;

/*import com.mongodb.MongoClient;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
/*import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;*/
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner{


    public static final Logger LOGGER= LoggerFactory.getLogger(Application.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }


    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Running DDLs now!");

        jdbcTemplate.execute("DROP TABLE IF EXISTS CUSTOMERS");
        jdbcTemplate.execute("CREATE TABLE CUSTOMERS ( customer_id INTEGER AUTO_INCREMENT, first_name varchar(255) , last_name varchar(255), primary key(customer_id))");

        List<Object[]> collect = Arrays.asList("Vallimalar Karthick,Karthick Sundaram,Alagu Karthick,Sivakami Karthick".split(",")).stream()
                .map((it) -> it.split(" "))
                .collect(Collectors.toList());

        collect.forEach(it->{LOGGER.info("Insert a Customer - FirstName {} and LastName {}", it[0],it[1]);});
        jdbcTemplate.batchUpdate("insert into CUSTOMERS (first_name, last_name) values(?,?)", collect);

        LOGGER.info("Querying for User with Lastname Karthick");
        List<Customer> result = jdbcTemplate.query("select * from CUSTOMERS where last_name=?", new Object[]{"Karthick"}, (rs, rowNum) -> new Customer(rs.getLong("customer_id"), rs.getString("first_name"), rs.getString("last_name")));
        LOGGER.info("Results are {}", result);

        LOGGER.info("Complete!!!");
    }

    /*@Bean
    public MongoClient mongoClient(){
        MongoClient mongoClient = new MongoClient("cluster0-bii6g.gcp.mongodb.net");
        return mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient){
        MongoTemplate mongoTemplate=new MongoTemplate(mongoClient,"test");
        return mongoTemplate;
    }
*/
}
