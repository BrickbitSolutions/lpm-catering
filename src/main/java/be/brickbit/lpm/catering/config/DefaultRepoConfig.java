package be.brickbit.lpm.catering.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(basePackages = "be.brickbit.lpm.catering.repository", entityManagerFactoryRef =
        "cateringEntityManagerFactory", transactionManagerRef = "cateringTransactionManager")
@EnableTransactionManagement
public class DefaultRepoConfig {
    @Bean
    @Primary
    PlatformTransactionManager cateringTransactionManager(@Qualifier("cateringEntityManagerFactory") final EntityManagerFactory
                                                              factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean cateringEntityManagerFactory(final EntityManagerFactoryBuilder builder) {
        return builder.dataSource(cateringDataSource()).packages("be.brickbit.lpm.catering.domain").persistenceUnit
                ("catering").build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource cateringDataSource() {
        return DataSourceBuilder.create().build();
    }
}
