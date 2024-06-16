package co.com.poli.showtimesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ShowtimesserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowtimesserviceApplication.class, args);
    }

}

