# Building RESTful WS with Spring Boot 3
Banuprakash C

Full Stack Architect, Corporate Trainer

Co-founder & CTO: Lucida Technologies Pvt Ltd., 

Email: banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/REST

===================================

Softwares Required:
1)  openJDK 17
https://jdk.java.net/java-se-ri/17

2) IntelliJ Ultimate edition 
https://www.jetbrains.com/idea/download/?section=mac

OR

Eclipse for JEE  
	https://www.eclipse.org/downloads/packages/release/2022-09/r/eclipse-ide-enterprise-java-and-web-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

```
a) docker pull mysql

For Windows:
b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i local-mysql bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```

Introduction to Spring and Spring Boot.
RESTful WS
JPA -> MySQL
Cache, AOP, Validation, testing
HATEOAS,
Async, Events, Reactive
Security, MS

Spring and Spring Boot:
Dependency Injection --> SOLID Design Principle

Spring --> provides a lightweight container for Dependency Injection [Inversion Of Control container] using which we can build enterprise application

Bean: object instantiated by container and or managed by the container

Manage --> take care of life-cycle / wiring

Traditional:
```
Controller
    Service service = new AdminService();

AdminService
    Repo repo = MySQLRepo();
```

IoC Container:
MySQLRepo object is given to AdminService;
AdminService object is given to Controller

Simple Application:
```
interface BookRepository {
    void addBook(Book book);
}

class BookRepositoryDbImpl implements BookRepository {
     public void addBook(Book book) {
        // SQL insert into ...
     }
}

class BookRepositoryMongoImpl implements BookRepository {
     public void addBook(Book book) {
        // db.collections.insert(book)
     }
}
// OCP Principle; Closed for Change, Open for Extension
class AppService {
    private BookRepository repo ; // loose coupling

    public void setRepo(BookRepository repo) {
        this.repo = repo;
    }

    public void insertBook(Book b) {
        this.repo.addBook(b);
    }
}
```

XML as Metadata:
beans.xml

```
    <beans>
        <bean    class="pkg.BookRepositoryDbImpl" />
        <bean id="bookMongo" class="pkg.BookRepositoryMongoImpl" />
        <bean id="service" class="pkg.AppService">
            <property name="repo" ref="bookMongo" />
        </bean>
    </beans>
```

property name="bookRepo"  ==> service.setRepo(bookMongo);

```
// creates a Spring container with metadata present in "beans.xml"
main() {
ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
ApplicationContext interface for Spring Container

AppService service = ctx.getBean("service", AppService.class);
    service.insert(new Book(...))
}
```

Annotations as Metadata instead of XML:
1) Spring has pre-defined annotations when applied at class level will creates instances of the class
@Component
@Repository
@Service
@Controller
@RestController
@Configuration
@ControllerAdvice

2) Autowired annotation for wiring
```
interface BookRepository {
    void addBook(Book book);
}

@Repository
class BookRepositoryDbImpl implements BookRepository {
     public void addBook(Book book) {
        // SQL insert into ...
     }
}

@Service
class AppService {
    @Autowired
    private BookRepository repo ; // loose coupling


    public void insertBook(Book b) {
        this.repo.addBook(b);
    }
}
```
@Repository uses sql-error-codes.xml

```
    try {


    } catch(SQLException ex) {
        if(ex.getErrorCode() == 1062) {
            throw new DuplicateKeyException(...)
        }
    }

```

Interllij --> New Project --> Spring Initilizer --> JDK 17, Java and Maven, setup artifactId and groupId


```
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
</dependency>
```

Spring Boot Framework in built on top of Spring Framework
Spring Boot 2.x version is built on top of Spring Framework 5.x
Spring Boot 3.x version is built on top of Spring Framework 6.x

Why Spring Boot?
* Highly opiniated framework
* Lots of configurations comes out of the box

Example:
If we want to use RDBMS, Spring Boot configures Database connection pool
If we want to build web application, configures Tomcat Web Container,
If we want ORM application, configures Hibernate out of the box
It's easier to dockerize the application


```

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

SpringApplication.run is similar to

ApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.setBasePackage("com.adobe.demo");
ctx.refresh();

// for example if we have a class with @Service in "com.example" package, it's not scanned


@SpringBootApplication is 3 in one:
1) @ComponentScan("com.adobe.demo")
2) @EnableAutoConfiguration --> creating built-in objects based on requirement, like database connection pool, Embedded Tomcat Container, ...
3) @Configuration

Object --> demoApplication
```





