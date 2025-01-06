package global;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class GlobalApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalApplication.class, args);
    }

}
