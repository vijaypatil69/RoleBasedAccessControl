# RoleBasedAccessControl
 
Role-Based Access Control (RBAC) System
Overview
The Role-Based Access Control (RBAC) system is a robust authentication and authorization framework designed to
 manage user access and control based on roles within an application. It is built using Spring Boot, JWT (JSON Web Token),
 and an H2 in-memory database for simple storage during development. This system ensures secure access to resources by
 managing user roles and permissions while enabling flexible, scalable user management.

Key Features
User Registration:

New users can register with essential details such as their first name, last name, email, mobile number,
 password, gender, and role.
Passwords are securely encrypted using BCrypt hashing for secure storage.
The system assigns roles such as user or admin, dictating what resources they can access.

User Authentication (Login):

Users authenticate using their email and password.
Upon successful authentication, a JWT is generated and returned. This JWT contains the user's email and role information.
The token is used for subsequent requests to verify the user's identity.

Role-Based Access Control (RBAC):

The system enforces different access levels based on the user’s role (e.g., admin or user).
Admin users have access to administrative endpoints such as creating and managing users.
Regular users can only access their own profile.
User Profile Management:

Authenticated users can view their profile details (first name, last name, email, role) by sending the 
JWT token in the request header.

User Logout:

Upon logout, the JWT token is removed from the active session, ensuring that it can no longer be used for future requests.


Technologies Used
Spring Boot: To build the backend API with minimal configuration.
Spring Data JPA: For managing database interactions.
H2 Database: An in-memory database for quick setup and testing.
JWT (JSON Web Token): For managing stateless user authentication and authorization.
Spring Security (custom implementation): Used for ensuring security with JWT-based authentication and role management.
BCrypt: A cryptographic algorithm used for securely hashing passwords.
Bouncy Castle: To enhance cryptographic operations.

Database Configuration
This project uses H2 Database for local storage in development:


properties
Copy code
spring.datasource.url=jdbc:h2:mem:h2db:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
The H2 console is enabled at /h2-console, allowing you to interact with the database via a web interface.



Dependencies
Below are the dependencies required for this project:

xml
Copy code
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
    </dependency>

    <dependency>
        <groupId>org.bouncycastle</groupId>
        <artifactId>bcprov-jdk15to18</artifactId>
        <version>1.78</version>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
</dependencies>

These dependencies support JWT, H2 database operations, and other essential Spring Boot functionalities.

Authentication and Authorization
Authentication
Authentication verifies the identity of a user by comparing provided credentials with stored data. 
This application uses JWT to manage user authentication. When users log in with their credentials (email and password),
 they receive a JWT that identifies them. This token is then included in request headers for all subsequent API 
calls to ensure the user’s identity is authenticated.

Authentication Workflow:

User logs in with email and password.
The backend verifies the credentials.
If successful, a JWT token is generated.
The client stores the token for future requests.
Login API (POST /user/login)
Request Body:

json
Copy code
{
    "emailId": "user@example.com",
    "password": "password123"
}
Response:

json
Copy code
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
The JWT is signed using a secret key and is valid for 8 hours. This ensures that the token is only valid for a
 limited period, providing an additional layer of security.

Password Encryption
Passwords are hashed using the BCrypt algorithm to ensure they are stored securely. BCrypt provides a strong, 
one-way hash function, which makes it difficult to retrieve the original password from the hash.

Passwords are never stored in plain text in the database.
When a user logs in, the application compares the entered password with the hashed version stored in the database.
Password Hashing in Registration:
java
Copy code
@Autowired
private BCryptPasswordEncoder passwordEncoder;

public String registerUser(UserRequestBody userRequestBody) {
    String hashedPassword = passwordEncoder.encode(userRequestBody.getPassword());
    userRepository.save(new User(userRequestBody, hashedPassword));
    return "User registered successfully!";
}
Authorization
Authorization controls what authenticated users are allowed to do. This application implements Role-Based Access Control 
(RBAC), where users are assigned roles, and these roles determine what resources and actions they can access.

Admin role: Full access to all endpoints, including managing users.
User role: Limited access, primarily to view their own profile.
Role-based Access:
Roles are stored as claims in the JWT and checked on each request.

Example:

If a user with the user role tries to access an admin resource, they will be denied access.
Admins can access all resources, including protected admin endpoints.
Security Features Implemented:

Password Hashing: All passwords are hashed using BCrypt before being stored in the database.
JWT-based Authentication: Stateless authentication using signed JWTs to ensure that the server does not need to 
store session information.

Token Expiration: JWT tokens expire after 8 hours, enhancing security by limiting the window for unauthorized use.
Role-Based Access Control (RBAC): Only users with specific roles (like admin) can access certain protected resources.
API Endpoints


1. User Registration (POST /user/register)
This API allows new users to register with their personal details. The password is securely hashed before being saved.

Request Body:

json
Copy code
{
    "firstName": "Vijay",
    "lastName": "Patil",
    "emailId": "vijay@example.com",
    "mobileNumber": "0123456789",
    "password": "Om12345678",
    "gender": "male",
    "role": "user"
}
Response:

json
Copy code
{
    "message": "User registered successfully!"
}


2. User Login (POST /user/login)
Users authenticate by providing their email and password. If successful, a JWT token is returned.

Request Body:

json
Copy code
{
    "emailId": "vijay@example.com",
    "password": "Om12345678"
}
Response:

json
Copy code
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}


3. User Profile (POST /user/profile)
This endpoint returns the profile of the authenticated user. The JWT token is sent in the request header for authentication.

Request Header:

makefile
Copy code
Authorization: <JWT_TOKEN>
Response:

json
Copy code
{
    "emailId": "vijay@example.com",
    "firstName": "Vijay",
    "lastName": "Patil",
    "gender": "male",
    "role": "user"
}


4. User Logout (POST /user/logout)
The user sends the JWT token in the header, and the system removes it from the active session, invalidating the token.

Request Header:

makefile
Copy code
Logout: <JWT_TOKEN>
Response:

json 

Copy code
{
    "message": "User logged out successfully!"
}



Conclusion
This RBAC system provides a highly secure framework for user management in modern web applications. 
By leveraging JWT for stateless authentication and BCrypt for password hashing, it ensures that users' identities and 
credentials are protected. The role-based access further secures the system by restricting resources based on user roles,
 making it a comprehensive and flexible solution for managing user permissions and access in your application.






















