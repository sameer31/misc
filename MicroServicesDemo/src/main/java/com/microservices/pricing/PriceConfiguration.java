package com.microservices.pricing;

import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
@ComponentScan
@EntityScan("com.microservices.pricing")
@EnableJpaRepositories("com.microservices.pricing")
@PropertySource("classpath:config/dbconfig.properties")
public class PriceConfiguration {

	@Bean
	public DataSource dataSource() {

		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript(
				"classpath:config/dbscripts/pricingschema.sql").build();
		return dataSource;
	}
}
