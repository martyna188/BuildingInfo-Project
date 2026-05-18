package pl.put.poznan.buildinginfo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application entry point for the Building Info REST API.
 * @author Krzysztof
 * @author Martyna
 * @author Damian
 * @version 1.0.1
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.buildinginfo.rest"})
public class BuildingInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildingInfoApplication.class, args);
    }
}
