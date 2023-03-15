package es.netmind.banana_invoices.config;

import es.netmind.banana_invoices.persistence.IPropietarioRepo;
import es.netmind.banana_invoices.persistence.IReciboRepo;
import es.netmind.banana_invoices.persistence.JPAIReciboRepo;
import es.netmind.banana_invoices.persistence.JPAPropietarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class ReposConfig {
    
	
	@Autowired
	private Environment env;
	
	@Bean // Esto sustituye al XML de configuración
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(bananaEmf().getObject());
		return transactionManager;
	}
	
	@Bean
	DataSource bananaDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(env.getProperty("invoices.driverClassName"));
		ds.setUrl(env.getProperty("invoices.url"));
		ds.setUsername(env.getProperty("invoices.dbUserName"));
		ds.setPassword(env.getProperty("invoices.password"));
		return ds;
	}
	
	@Bean
	public JpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
		va.setShowSql(true);
		va.setGenerateDdl(true);
		return va;
	}
		
	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", env.getProperty("invoices.dialect")); 
		return properties;
	}
	
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean bananaEmf() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(bananaDataSource());
		em.setPersistenceUnitName(env.getProperty("invoices.persistenceUnitName"));
		em.setPackagesToScan("es.netmind.banana_invoices.*");
		em.setJpaVendorAdapter(vendorAdapter());
		em.setJpaProperties(additionalProperties());
		return em;
	}
	
	@Bean
	public IPropietarioRepo iPropietarioRepo() {
		return new JPAPropietarioRepo();
	}
	
	@Bean
	public IReciboRepo iReciboRepo() {
		return new JPAIReciboRepo();
	}
	
}
