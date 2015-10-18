package be.brickbit.lpm.core.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "be.brickbit.lpm.core.repository", entityManagerFactoryRef = "userEntityManagerFactory", transactionManagerRef = "userTransactionManager")
@EnableTransactionManagement
public class AuthRepoConfig {
    @Bean
    PlatformTransactionManager userTransactionManager(@Qualifier("userEntityManagerFactory") final EntityManagerFactory factory)
    {
        return new JpaTransactionManager(factory);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean userEntityManagerFactory(final EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(userDataSource())
                .packages("be.brickbit.lpm.core.domain")
                .persistenceUnit("users")
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.auth")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }
}
