# Controle de Votações

App que recebe pautas de votação, agendando sua data de início e término. O app também permite votar nas pautas quando estas estiverem 
ativas. Ao término do prazo de votação de uma pauta ocorre uma totalização e seu resultado é enviado para uma fila para posterior consumo. Também é disponibilizado uma api para listagem das pautas presentes no sistema.

Dependências

Docker
Docker-compose
OpenApi
OpenFeign
RabbitMq
MongoDB


  
  Gerar a imagem:
    mvn package
    
   Rodar a aplicação:
     docker-compose up -d
     Executa o docker-compose para subir mongoDb, rabbitMq e Consul
     
Documentação
  http://localhost:30005/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
    
