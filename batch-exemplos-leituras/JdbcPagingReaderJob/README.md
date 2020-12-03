# Poc spring batch com leitura páginada utilizando jdbc    
  
Poc de API para leitura paginada de dados em um banco de dados relacional
  
### Versão  
- v0.0.1-SNAPSHOT

### Linguagem

- Java na versão 1.8
- Spring Boot na versão 2.2.7.RELEASE
- Spring Batch referente a versão do Spring Boot

### Problemas encontrados

- Ao utilizar a tolerância a falhas no Spring batch a leitura não é finalizada cajo haja algum erro 
na leitura dos dados.
  
### Subindo o projeto localmente      
  
- Criar base de dados no mysql localmente, recomendo usar **docker**
    Você pode seguir o passo a passo em [docke-hub/mysql](https://hub.docker.com/_/mysql)

- Não possui variável de ambiente, porém, há propriedades definidas no application.properties 
    
```
    spring.datasource.jdbcUrl=jdbc:mysql://localhost:33606/spring_batch
    spring.datasource.username=root
    spring.datasource.password=my-secret-pw

    app.datasource.jdbcUrl=jdbc:mysql://localhost:33606/app
    app.datasource.username=root
    app.datasource.password=my-secret-pw
    spring.batch.initialize-schema=always

    # Adicionar para mostrar no banco de dados
    #logging.level.org.springframework.batch.item.database=DEBUG

```

### Não se esquecer de rodar os scripts no mysql

- Rodar o script em resources/schema.sql e data.sql no console ou configurando o spring para fazer isso

### Idea utilizada

- IntelliJ (Por favor use com moderação)
   