# Configurações para o perfil 'test'
		# Use um banco de dados em memória para testes rápidos
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

		# Configurações JPA/Hibernate para o perfil 'test'
spring.jpa.hibernate.ddl-auto=create-drop # Cria e dropa as tabelas em cada execução de teste
spring.jpa.show-sql=false # Desativa logs de SQL nos testes, se desejar
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

# Desativa a segurança nos testes se você não quiser testar a segurança em cada teste de unidade
# spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8080/realms/your_realm/protocol/openid-connect/certs
		# spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/your_realm