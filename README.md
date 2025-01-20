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

Error:
Field bookRepo in com.adobe.demo.service.AppService required a single bean, but 2 were found:
	- bookRepoDbImpl
	- bookRepoMongoImpl

Solution 1:
Making one as @Primary
make one of them as @Primary

Solution 2:
using @Qualifier

```
@Service
public class AppService {
    @Autowired
    @Qualifier("bookRepoDbImpl")
    private BookRepo bookRepo;
```

Solution 3:
based on Profile, only beans which are eligible for the profile will be created within the container

```
@Repository
@Profile("dev")
public class BookRepoMongoImpl implements BookRepo{

@Repository
@Profile("prod")
public class BookRepoDbImpl implements  BookRepo{

how to choose profile:
a) application.properties
spring.profiles.active=dev

b) Command Line Arguments: higher priority compared to application.properties
More Run/Debug -> Modify Run Confuration
Active Profiles: prod

```

Solution 4:
ConditionalOnMissingBean
```
@Repository
//@Profile("dev")
@ConditionalOnMissingBean( name = "bookRepoDbImpl")
public class BookRepoMongoImpl implements BookRepo{

```
https://www.tutorialspoint.com/spring/spring_architecture.htm
https://www.tutorialspoint.com/spring/spring_ioc_containers.htm

Factory Method and Building RESTful WS with JPA

================

Recap day1:
Dependency Injection, Inversion Of Controller container
Spring Framework vs Spring Boot
Metadata --> XML or Annotation

Spring Container can be accessed using ApplicationContext interface
BeanFactory is also an interface using which we can access Spring Container

BeanFactory:
1) Bean instantiation
2) Wiring

ApplicationContext is a super-set of Bean Factory
1) Bean instantiation
2) Wiring
3) AOP
4) Multiple context [ containers]

Annotations at class-level: @Component, @Repository, @Service, ... [7 annotations]
@Autowired can be used to wire dependencies [ wiring by type, @Primary, @Qualifier, @Profile, @ConditionalOnMissingBean, @ConditionalOnProperty]

==========

Day 2:
* Scope of bean
1) Singleton by default [ only one bean of a class is present inside the container]
2) Prototype
@Scope("prototype")
A seperate bean is wired for each dependency

```
@Service
public class AppService {
    @Autowired
    private BookRepo bookRepo;

@Service
public class AdminService {
    @Autowired
    private BookRepo bookRepo;



3) RequestScope: applicable only for web application and not standalone apps
@RequestScope
or
@Scope("request")

one bean per request

@Repository
@RequestScope
public class BookRepoDbImpl implements  BookRepo{

4) SessionScope: applicable only for web application and not standalone apps
@SessionScope

one bean per session
Session: Conversational state of a client

@Repository
@SessionScope
public class BookRepoDbImpl implements  BookRepo{

```

Factory Method 
The Factory Method pattern suggests that you replace direct object construction calls (using the new operator) with calls to a special factory method. 

* Object creation is complex
* we need objects of 3rd party classes
* Spring instantiates classes if it contains above mentioed 7 annotations : @Component, @Repository, @Service, ... [7 annotations]

Maven Central repository — com.mchange:c3p0:0.10.1

we get "ComboPooledDataSource" class to create a database connection pool.

ComboPooledDataSource doesn't contain above mentioed 7 annotations
DataSource: Pool of database connection

Solution:
```
@Configuration 
public class AppConfig {

    // factory method; returned object is managed by Spring Container
    @Bean("postgres")
    public DataSource getSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
        cpds.setUser("swaldman");
        cpds.setPassword("test-password");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds; 
    }

    @Bean("mysql")
    public DataSource getSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.Driver" ); //loads the jdbc driver
        ...
        return cpds; 
    }
}

@Repository
public class BookRepoDbImpl implements  BookRepo {
    @Autowired
    @Qualifier("postgres")
    DataSource postgresds; // connection pool is wired

     @Autowired
    @Qualifier("mysql")
    DataSource mysqlds; // connection pool is wired
}

```

Docker Desktop

docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql

mysql: image --> application; mysql@8.2.3 or mysql@latest -==> using tags
local-mysql: name of the container --> running within the docker 
container runs on port 3306 -> exposed as 3306 to application outside of container

====================

Java Persistence API :JPA

ORM -> Object Relational Mapping

```
    @Entity
    @Table(name="books")
    public class Book {
        @Id
        private String isbn;

        @Column(name="title")
        private String bookTitle;

        @Column(name="amount")
        private double price;
    }

```

Once Mapping is done; ORM frameworks helps in DDL and DML operations; simplifies

 public void addProduct(Product product)
    em.persist(product);
 }

 ORM Frameworks: Hibernate, TopLink, KODO, JDO, OpenJPA, EclipseLink, ....
Hibernate --> JBOSS --> RedHat
TopLink --> Oracle
KODO --> BEA --> Oracle
JDO --> Sun MS --> Oracle
OpenJPA --> Apache

JPA: Specification for ORM 

Below code is not required in Spring Boot, required if we are using Spring Framework
```
@Configuration 
public class AppConfig {

    // factory method; returned object is managed by Spring Container
    @Bean
    public DataSource getSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
        cpds.setUser("swaldman");
        cpds.setPassword("test-password");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds; 
    }

    @Bean
    public EntityManagerFactory emf(DataSource ds) {
        LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory();
        emf.setDataSource(ds); // which db pool to be used
        emf.setJpaVendor(new HibernateJpaVendor()); //which ORM to use
        emf.setPackagesToScan("com.adobe.prj.entity"); // where are my entities?
        ..
        return emf;
    }
}

@Repository
public class BookRepoDbImpl implements  BookRepo {
    @PersistenceContext
    EntityManager em;

    public void addBook(Book b) {
        em.persist(b);
    }
}
```


New Application with the following depdencies:
1) MySQL
2) Lombok [ reduce boilerplate code]
3) JPA: Spring Data JPA --> gives Hibernate as ORM an HikariCP for DB Connection pool


Spring Data JPA creates database connection pool based on entiries present in 
"application.properites" or "application.yml" file

https://docs.spring.io/spring-boot/appendix/application-properties/index.html

1) spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

ORM should generate SQL compatabile with MySQL

2) spring.jpa.hibernate.ddl-auto=update

update --> map class to existing table, it table doesn't exist create a new one, if required alter the table

create --> drop table on application exit, create a new one for every run of application [ useful only for test env]

validate -> map to existing tables. Don't allow alter or creation. [ Bottom to top approach]

RESTFUL_DB?createDatabaseIfNotExist=true

create database RESTFUL_DB;

