## message-scheduler
API Java para agendamento de envio de mensagens

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5d43097312264efeaa9c1309cace98f4)](https://app.codacy.com/gh/bosofelipe/message-scheduler?utm_source=github.com&utm_medium=referral&utm_content=bosofelipe/message-scheduler&utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/bosofelipe/message-scheduler.svg?branch=main)](https://travis-ci.org/bosofelipe/message-scheduler)

##### Pré requisitos
Para iniciar a aplicação é necessário a instalação de algumas ferramentas
- Git:
    https://git-scm.com/book/en/v2/Getting-Started-Installing-Git/
- Gradle :
    https://github.com/gradle/gradle/blob/master/README.md
- Docker :
    https://docs.docker.com/engine/install
- Docker compose:
    https://docs.docker.com/compose/install/
    
##### Instalação e configurações de ambiente        

- Clonar o repositório: `git clone https://github.com/bosofelipe/message-scheduler.git`
- Entrar na pasta: `cd message-scheduler/`
- Rodar os seguintes comandos
`gradle build && docker build ./ -t messageschedulerapp`
`docker-compose up`

#### Documentação da API
`http://localhost:8080/message-scheduler/swagger-ui.html`

#### Postman
Os endpoints da API foram disponibilizados como collection do POSTMAN
-Baixar `Postman`

##### Tecnologias utilizadas
- [Java](https://www.oracle.com/br/java/) Java 11
- [Spring Boot](https://spring.io/projects/spring-boot) Framework Java/Kotlin
- [Postgres](https://www.postgresql.org/) Banco de dados Relacional
- [Gradle](https://gradle.org/) Gradle, para gerenciamento de dependencias
- [Docker](https://www.docker.com/) Para facilitar a instalação e inicialização da API, utilizando containers

## Licença
[GNU AFFERO GENERAL PUBLIC LICENSE V3.0](https://github.com/bosofelipe/message-scheduler/blob/main/LICENSE)
