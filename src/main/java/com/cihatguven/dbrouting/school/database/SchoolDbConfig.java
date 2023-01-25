package com.cihatguven.dbrouting.school.database;

import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(
        basePackages = "com.cihatguven.dbrouting.school",
        entityManagerFactoryRef = "schoolEntityManagerFactory",
        transactionManagerRef = "schoolTransactionManager"
)
public class SchoolDbConfig {


    private final DataSourceRouting dataSourceRouting;

    @Bean
    public LocalContainerEntityManagerFactoryBean schoolEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceRouting);
        em.setPackagesToScan("com.cihatguven.dbrouting.school");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaPropertyMap(HibernatePropertiesSchool.get());
        return em;
    }

    @Bean
    public PlatformTransactionManager schoolTransactionManager( @Qualifier("schoolEntityManagerFactory") final EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}