package be.brickbit.lpm.catering;

import javax.persistence.EntityManager;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import be.brickbit.lpm.catering.config.TestDefaultRepoConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestDefaultRepoConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		FlywayTestExecutionListener.class })
@Transactional
@FlywayTest
@ActiveProfiles("test")
public abstract class AbstractRepoIT {
	@Autowired
	private EntityManager entityManager;

	protected void insert(Object... entities) {
		for (Object entity : entities) {
			save(entity);
		}
	}

	private void save(Object object) {
		if (entityManager.contains(object)) {
			entityManager.merge(object);
		} else {
			entityManager.persist(object);
		}
	}
}
