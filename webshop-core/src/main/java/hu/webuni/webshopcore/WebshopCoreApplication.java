package hu.webuni.webshopcore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopCoreApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(WebshopCoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Webshop are up and running");
    }
}
