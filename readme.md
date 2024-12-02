## **Movie Ticket Reservation Backend**


This is the backend service for the Movie Ticket Reservation system, built using **Java**, **Spring Boot**, and **MySQL**.

### **Prerequisites**

1. **IntelliJ IDEA**: This project is optimized for development using IntelliJ IDEA.
2. **MySQL Database**: The project uses MySQL as the database. Ensure that you have MySQL installed and running on your system.
3. **Java Development Kit (JDK)**: Version 17 or higher is required.

### **Setup Instructions**


**1. Clone the Repository**

Clone the repository to your local machine.

```other
git clone https://github.com/sabasgh/backEnd.git
cd movie-ticket-reservation
```


**2. Configure the Database**

Update the database username and password in the application.properties file located at src/main/resources.

```other
spring.datasource.url=jdbc:mysql://localhost:3306/movie_ticket_reservation
spring.datasource.username=<your-database-username>
spring.datasource.password=<your-database-password>
spring.jpa.hibernate.ddl-auto=update
```


Replace add with your MySQL credentials.

The program itself automatically adds the entries for the movies and showtimes. You can also utilize the sql dump file that we have provided.

**3. Build the Project**

Open the project in IntelliJ IDEA and build the program.

**4. Run the Application**

Run the application using the following command:

```other
./mvnw spring-boot:run
```


Alternatively, you can run the application directly from IntelliJ IDEA by selecting the main class and clicking **Run**. Usually this method works better.

**5. Database Schema**

The database schema will be created automatically when the application runs for the first time. Ensure that the specified database (movie_ticket_reservation) exists in MySQL.

**6. Test the APIs**

Once the application is running, you can test the APIs using tools like **Postman** or **curl**. The base URL for all endpoints will be:

```other
http://localhost:8080
```


**Key Note:**


- **Database Configuration**: Update application.properties with your MySQL credentials as described above.

**Troubleshooting**

• **Database Connection Issues**: Verify that MySQL is running and accessible at the specified URL.

• **Dependency Errors**: Run mvn clean install to resolve any dependency issues.
