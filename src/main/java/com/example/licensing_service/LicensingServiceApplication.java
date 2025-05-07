package com.example.licensing_service;

import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import com.netflix.discovery.Jersey3DiscoveryClientOptionalArgs;
import com.netflix.discovery.shared.transport.jersey.TransportClientFactories;
import com.netflix.discovery.shared.transport.jersey3.Jersey3TransportClientFactories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LicensingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicensingServiceApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	@ConditionalOnMissingBean(AbstractDiscoveryClientOptionalArgs.class)
	public Jersey3DiscoveryClientOptionalArgs jersey3DiscoveryClientOptionalArgs() {
		return new Jersey3DiscoveryClientOptionalArgs();
	}

	@Bean
	@ConditionalOnMissingBean(TransportClientFactories.class)
	public Jersey3TransportClientFactories jersey3TransportClientFactories() {
		return Jersey3TransportClientFactories.getInstance();
	}
}
