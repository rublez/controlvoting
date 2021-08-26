package br.com.sicredi.controlvoting.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.sicredi.controlvoting.repository")
public class MongoConfiguration extends AbstractMongoClientConfiguration {

	@Value("${spring.data.mongodb.uri}")
	private String connectionStr;
	
	@Value("${spring.data.mongodb.database}")
	private String databaseName;
	
	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);

	}

	@Override
	protected String getDatabaseName() {
		return databaseName;

	}

	@Override
	public MongoClient mongoClient() {
		final ConnectionString connectionString = new ConnectionString(connectionStr);
		final MongoClientSettings mongoClientSettings = MongoClientSettings
				.builder()
				.applyConnectionString(connectionString)
				.build();
		
		return MongoClients.create(mongoClientSettings);

	
	}

}
