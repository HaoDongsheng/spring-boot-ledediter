package org.hds.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = "org.hds.mapper", sqlSessionFactoryRef = "PrimarydbSqlSessionFactory")
public class DruidDBConfig {
	private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);

	@Value("${spring.datasource.Primarydb.url}")
	private String dbUrl;

	@Value("${spring.datasource.Primarydb.username}")
	private String username;

	@Value("${spring.datasource.Primarydb.password}")
	private String password;

	@Value("${spring.datasource.Primarydb.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.filters}")
	private String filters;

	@Value("{spring.datasource.connectionProperties}")
	private String connectionProperties;

	@Bean(name = "PrimaryDataSource") // 声明其为Bean实例
	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
	public DataSource PrimarydataSource() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(this.dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);

		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		datasource.setConnectionProperties(connectionProperties);

		return datasource;
	}

	@Bean(name = "PrimarydbSqlSessionFactory")
	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
	public SqlSessionFactory sqlSessionFactory(@Qualifier("PrimaryDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
//		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//				.getResources("classpath:src/main/java/org/hds/logmapper/*.xml"));
		return sessionFactoryBean.getObject();
	}
}