package com.teamgold.goldenharvestgateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GoldenHarvestGatewayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GoldenHarvestGatewayApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }

}
