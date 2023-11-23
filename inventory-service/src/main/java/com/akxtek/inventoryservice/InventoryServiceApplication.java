package com.akxtek.inventoryservice;

import com.akxtek.inventoryservice.model.Inventory;
import com.akxtek.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner( InventoryRepository inventoryRepository) {
		return args -> {
			inventoryRepository.save(Inventory.builder()
					.skuCode("12345")
					.stock(0)
					.build());
			inventoryRepository.save(Inventory.builder()
					.skuCode("12346")
					.stock(100)
					.build());
			inventoryRepository.save(Inventory.builder()
					.skuCode("12347")
					.stock(1000)
					.build());

		};
	}
}
