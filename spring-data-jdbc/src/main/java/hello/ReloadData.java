package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReloadData implements CommandLineRunner {

    public static final Logger LOGGER= LoggerFactory.getLogger(ReloadData.class);

    @Autowired
    private CustomerService customerService;

    @Override
    public void run(String... args) throws Exception { // This comes from CommandLineRunner. This is king of initialization phase for the application.
        customerService.dropDBAndCreateNewData();
    }
}

