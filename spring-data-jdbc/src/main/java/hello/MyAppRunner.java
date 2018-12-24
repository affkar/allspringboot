package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyAppRunner implements ApplicationRunner {

    public static final Logger LOGGER= LoggerFactory.getLogger(MyAppRunner.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Option 1 Name is {}" ,args.getOptionNames().toArray()[0]);
        LOGGER.info("Option 1 Value is {}" ,args.getOptionValues(args.getOptionNames().toArray()[0].toString()).toArray()[0]);
        LOGGER.info("NonOption is {}" ,args.getNonOptionArgs().toArray()[0]);

    }
}
