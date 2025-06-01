# 🛒 Store Management System

A Spring Boot-based microservices architecture designed for managing store operations. This project is a work in progress, showcasing a modular, scalable approach using a multi-module Maven setup.

## 📦 Modules

The project is organized :

- **product-service**: Manages product-related operations, including CRUD functionality for products.
- **order-service**: Handles order creation, updates, and retrieval.
- **inventory-service**: Tracks and manages inventory levels.
- **payment-service**: Processes payments and handles transaction-related operations.
- **shared-lib**: Contains shared utilities, models, and configurations used across services.

## 🚀 Getting Started

### Prerequisites

To set up and run the project, ensure you have the following installed:

- Java 17 or later
- Maven 3.8.x or later
- Docker + Docker Compose (for containerization)

### Build the Project

To build all modules, run the following command from the project root:

```bash
mvn clean install
```

### Run the project

Navigate to the compose directory and run the following command to start all services using Docker Compose:

```bash
KONG_DATABASE=postgres docker compose --profile database up --build
```

## 🧪 Testing the Product Service

The product-service is fully operational and ready for testing.

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Retrieve a list of products |
| GET | `/api/products/{id}` | Retrieve a product by ID |
| POST | `/api/products` | Create a new product |
| PUT | `/api/products/{id}` | Update an existing product |

### Authentication

The product-service uses HTTP Basic Authentication. Use the following credentials:

- **Username**: admin
- **Password**: adminpass

Include the Authorization header in your requests. Example using curl:

```bash
curl -u admin:adminpass http://localhost:8080/api/products
```

## 🛠️ Technologies Used

- **Spring Boot 3.4**: Framework for building microservices
- **Spring Security**: For authentication and authorization
- **Spring Data JPA**: For database operations
- **H2 Database**: In-memory database for development
- **Maven**: Build and dependency management
- **Docker**: Containerization for deployment

## 📈 Roadmap

- [x] Implement product-service
- [x] Set up multi-module Maven structure
- [ ] Develop inventory-service
- [ ] Develop order-service
- [ ] Develop payment-service
- [ ] Ensure high test coverage
- [ ] Write API documentation using Swagger
- [ ] Implement CI/CD pipeline with GitHub Actions
- [ ] Add Loki and Grafana for logging and monitoring
- [ ] Implement distributed tracing with OpenTelemetry
- [ ] Implement API gateway using Kong + a standalone database for storage
- [ ] Implement security as part of the API gateway using OAuth2 and JWT
- [ ] Add centralized configuration management using Spring Cloud Config
- [ ] Add caching for product reads using Redis

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add your feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## 📄 License

This project is licensed under the MIT License. See the LICENSE file for details.
