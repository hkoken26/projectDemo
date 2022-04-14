package com.reengen.demo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.reengen.demo.domain")
@EnableJpaRepositories("com.reengen.demo.repos")
@EnableTransactionManagement
public class DomainConfig {
}
