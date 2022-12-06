package hu.webuni.shippingservice;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ShippingServiceApplication implements CommandLineRunner {
	
    public static void main(String[] args) {

        SpringApplication.run(ShippingServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Shipping service are up and running");
        log.info("Waiting for parcels");
        
    }
}
