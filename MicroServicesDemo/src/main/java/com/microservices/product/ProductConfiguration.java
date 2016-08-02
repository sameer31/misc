package com.microservices.product;

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
@EntityScan("com.microservices.product")
@EnableJpaRepositories("com.microservices.product")
@PropertySource("classpath:config/dbconfig.properties")
public class ProductConfiguration {

	@Bean
	public DataSource dataSource() {

		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript(
				"classpath:config/dbscripts/productschema.sql").build();

		return dataSource;
	}
}
