## Stack do projeto:  
● Java 8+  
● Ferramentas de build: Maven  
● Banco de dados: H2  
● Stack: Spring Boot  
● Padrão REST  

### 1.) Download do código
### 2.) Procedimento para executar o código e deixar o backend disponível para consultas:
#### a.) Ir para o diretório onde o código foi baixado e executar:
mvn spring-boot:run

### 3.) Procedimento para executar os testes unitários e integrados do código
#### a.) Ir para o diretório onde o código foi baixado e executar:
mvn clean install

### 4.) Observações importantes:
#### a.) A base de dados do H2 é criada com alguns registros os quais são utilizados nos testes e podem ser utilizados pelo próprio backend.
#### b.) A url padrão local é: http://localhost:8080
#### c.) Exemplos:
1.) SELECT ALL AND RETURN CODE  
curl localhost:8080/rest/bankslips -X GET  
curl -I localhost:8080/rest/bankslips -X GET  
  
2.) INSERT  
curl -H "Accept: application/json" -H "Content-type: application/json" -d '{"due_date":"2018-11-04","total_in_cents":23400.56,"customer":"Cliente que vai pagar 2002"}' -X POST  localhost:8080/rest/bankslips  
