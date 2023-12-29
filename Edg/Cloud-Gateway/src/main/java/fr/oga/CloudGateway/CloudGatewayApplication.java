package fr.oga.CloudGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableEurekaClient
@EnableWebFluxSecurity
@RefreshScope
public class CloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}


	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(DiscoveryLocatorProperties dlp, ReactiveDiscoveryClient rdc){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
	}

}
