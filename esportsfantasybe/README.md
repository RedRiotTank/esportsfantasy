
# eSportsFantasy Backend

eSportsFantasy is a web and mobile application that implements a "fantasy" style game model in the world of electronic sports.

This is also my final degree project for the degree in Computer Engineering at the University of Granada.

In this repository you can find the backend system of the application.




## Deployment

To deploy this project, you can do it in two different ways

### All containers in the same server
//TODO

### Each container separately

To deploy the backend part of the project, clone the repository

```bash
  git clone https://github.com/RedRiotTank/esportsfantasybe.git
```

specify the database name and password in src/main/resources/application.properties

```
spring.datasource.url=jdbc:mysql://<DATABASE IP HERE>:3306/eSportsFantasyDB
spring.datasource.username=root
spring.datasource.password=<PASSWORD HERE>
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
}
```

create the .war file:
```bash
mvn compile
mvn package
```

create the docker image and run it:

```bash
docker build -t be .
docker run -d --name be -p 8080:8080 be
```