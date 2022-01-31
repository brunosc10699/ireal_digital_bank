# Ireal Digital Bank

Welcome to our digital bank. This REST API is designed to pratice OOP concepts.

## System Layers

![layers.jpg](https://raw.githubusercontent.com/brunosc10699/ireal_digital_bank/main/.github/images/layers.jpg)



## Requirements

1. Java 11
2. MSSQL Server



## Configuration

#### MSSQL Server database via Docker

Use these commands to run MSSQL Server in a Docker container ("dev" profile):

```shell
$ sudo docker run --name my-sqlserver -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=1@Aa23456" -p 1433:1433 -h my-sqlserver -d mcr.microsoft.com/mssql/server:2019-latest
```



## Documentation by Swagger

http://localhost:8080/swagger-ui/index.html (Login and password via environment variables or application.properties file)

