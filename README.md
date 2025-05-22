# Store Management Tool

A Spring Boot application for managing products in a store. This project provides a RESTful API for CRUD operations on products, with security, validation, error handling, and logging features.

## Features
- **Product Management**: Create, update, retrieve, and list products with pagination.
- **Validation**: Input validation for product data (name, description, price, quantity).
- **Error Handling**: Custom error responses for not found, duplicate, and validation errors.
- **Security**: HTTP Basic authentication with role-based access (ADMIN, USER).
- **Logging**: Request tracing with unique trace IDs and service method logging using AOP.
- **Testing**: Integration tests using Testcontainers and JUnit.

## Getting Started

### Prerequisites
- Java 21
- Maven
- Docker (for running tests with Testcontainers)

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/beowolf23/store-management.git
   cd store-management
   ```
2. **Build the project:**
   ```bash
   mvn clean install
   ```
3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
4. **Access the API:**
   - Base URL: `http://localhost:8080/api/products`
   - Use HTTP Basic Auth:
     - Admin: `admin` / `adminpass`
     - User: `user` / `userpass`

### Running Tests

Integration tests use Testcontainers and require Docker:
```bash
mvn test
```

## API Endpoints
- `POST /api/products` (ADMIN): Add a new product
- `PUT /api/products/{productId}` (ADMIN): Update a product
- `GET /api/products/{productId}`: Get product by ID
- `GET /api/products`: List products (with pagination)

## Future Work
- Implement JWT-based authentication
- Add Swagger/OpenAPI documentation
- Improve error messages and internationalization
- Concurrency control for product updates
- Add more unit and integration tests
- Implement caching for product retrieval
- Add a frontend interface (e.g., React or Angular)

## License
This project is for educational purposes.

