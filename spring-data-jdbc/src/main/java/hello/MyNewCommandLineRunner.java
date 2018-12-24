package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyNewCommandLineRunner implements CommandLineRunner {

    public static final Logger LOGGER= LoggerFactory.getLogger(MyNewCommandLineRunner.class);

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("I was executed as well.. with {}",args);
        LOGGER.info("No of Arguments with {}",args.length);
    }
}
