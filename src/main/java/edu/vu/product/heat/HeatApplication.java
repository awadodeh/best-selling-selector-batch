package edu.vu.product.heat;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class HeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeatApplication.class, args);
    }
}
