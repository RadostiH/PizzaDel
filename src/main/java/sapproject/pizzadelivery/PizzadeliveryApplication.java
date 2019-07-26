package sapproject.pizzadelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication

public class PizzadeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzadeliveryApplication.class, args);
    }

}
