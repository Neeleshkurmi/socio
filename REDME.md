# Socio - Social Media Backend API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-Development-yellow)

## 📋 Overview

Socio is a robust, scalable backend API for a modern social media platform built with Spring Boot. This application provides a complete RESTful API for user interactions, content sharing, and social networking features.

## ✨ Features

### 🔐 Authentication & Authorization
- JWT-based authentication
- Role-based access control (User, Admin)
- Secure password hashing
- Refresh token mechanism

### 👥 User Management
- User registration and profile management
- Follow/Unfollow system
- User search and discovery
- Profile customization

### 📱 Content Management
- Create, read, update, and delete posts
- Media upload support (images/videos)
- Like and comment system
- Post sharing functionality

### 💬 Social Features
- Real-time notifications
- Direct messaging system
- News feed with personalized content
- Hashtag and trending system

### ⚙️ Additional Features
- Email notifications
- API rate limiting
- Comprehensive logging
- Health monitoring endpoints

## 🛠️ Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database operations
- **Spring Web** - REST API development
- **Spring WebSocket** - Real-time features

### Database
- **PostgreSQL** - Primary database
- **Redis** - Caching and session management
- **Elasticsearch** - Search functionality

### Messaging & Queue
- **RabbitMQ/Kafka** - Event-driven architecture
- **WebSocket/STOMP** - Real-time communication

### Security
- **JWT** - Token-based authentication
- **Bcrypt** - Password encryption
- **Spring Security** - Security framework

### DevOps & Tools
- **Docker** - Containerization
- **Maven** - Build tool
- **Git** - Version control
- **Swagger/OpenAPI** - API documentation
- **Lombok** - Boilerplate reduction

## 📁 Project Structure

```
socio/
├── src/main/java/com/neelesh/socio/
│   ├── config/           # Configuration classes
│   ├── controller/       # REST controllers
│   ├── dto/             # Data Transfer Objects
│   ├── entity/          # JPA entities
│   ├── exception/       # Custom exceptions
│   ├── repository/      # Data access layer
│   ├── security/        # Security configuration
│   ├── service/         # Business logic layer
│   ├── util/            # Utility classes
│   └── SocioApplication.java
├── src/main/resources/
│   ├── application.properties
│   ├── static/
│   └── templates/
├── src/test/            # Test classes
├── docker/              # Docker configurations
├── docs/                # Documentation
├── Dockerfile
└── pom.xml
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Redis 6+
- Docker (optional)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Neeleshkurmi/socio.git
   cd socio
   ```

2. **Configure database**
   ```bash
   # Update application.properties with your database credentials
   # src/main/resources/application.properties
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Using Docker (alternative)**
   ```bash
   docker-compose up --build
   ```

### Running with Docker

```bash
# Build and run all services
docker-compose up

# Run specific services
docker-compose up postgres redis app

# Stop services
docker-compose down
```

## 🔧 Configuration

### Environment Variables

Create a `.env` file in the root directory:

```properties
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=socio_db
DB_USERNAME=postgres
DB_PASSWORD=your_password

# JWT
JWT_SECRET=your_jwt_secret_key_here
JWT_EXPIRATION=86400000

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379

# Email
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=your_email@gmail.com
EMAIL_PASSWORD=your_password
```

### Application Properties

Key configurations in `application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Key Endpoints

#### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - User login
- `POST /auth/refresh` - Refresh token
- `POST /auth/logout` - User logout

#### Users
- `GET /users/{id}` - Get user profile
- `PUT /users/{id}` - Update user profile
- `GET /users/search` - Search users
- `POST /users/{id}/follow` - Follow user
- `DELETE /users/{id}/follow` - Unfollow user

#### Posts
- `POST /posts` - Create new post
- `GET /posts/{id}` - Get post by ID
- `GET /posts/user/{userId}` - Get user's posts
- `PUT /posts/{id}` - Update post
- `DELETE /posts/{id}` - Delete post

#### Comments
- `POST /posts/{postId}/comments` - Add comment
- `GET /posts/{postId}/comments` - Get post comments
- `PUT /comments/{id}` - Update comment
- `DELETE /comments/{id}` - Delete comment

### Access API Documentation

Once the application is running:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🧪 Testing

### Run Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with coverage
mvn jacoco:report
```

### Test Categories
- Unit Tests (Service/Repository layer)
- Integration Tests (Controller layer)
- Security Tests
- Performance Tests

## 📊 Database Schema

### Key Entities
```
User
├── id (PK)
├── username
├── email
├── password
├── profile_picture
└── created_at

Post
├── id (PK)
├── content
├── user_id (FK)
├── media_url
└── created_at

Comment
├── id (PK)
├── content
├── user_id (FK)
├── post_id (FK)
└── created_at

Follow
├── follower_id (FK)
├── following_id (FK)
└── created_at

Like
├── user_id (FK)
├── post_id (FK)
└── created_at
```

## 🔒 Security

### Authentication Flow
1. User logs in with credentials
2. Server validates and returns JWT token
3. Client includes token in Authorization header
4. Server validates token for each request

### Protected Endpoints
- All POST, PUT, DELETE operations require authentication
- User-specific operations require ownership or admin role
- Rate limiting on authentication endpoints

## 📦 Deployment

### Prerequisites for Production
- SSL certificate (HTTPS)
- Load balancer
- Monitoring setup
- Backup strategy

### Deployment Steps

1. **Build for production**
   ```bash
   mvn clean package -Pproduction
   ```

2. **Docker deployment**
   ```bash
   docker build -t socio-backend .
   docker run -p 8080:8080 socio-backend
   ```

3. **Cloud deployment** (AWS example)
   ```bash
   # Deploy to AWS ECS/EC2
   # Configure RDS for database
   # Setup ElasticCache for Redis
   ```

## 🚨 Troubleshooting

### Common Issues

1. **Database connection failed**
    - Check PostgreSQL service status
    - Verify credentials in application.properties

2. **Port already in use**
   ```bash
   # Kill process on port 8080
   sudo lsof -ti:8080 | xargs kill -9
   ```

3. **JWT not working**
    - Ensure JWT secret is properly set
    - Check token expiration time

### Logs
```bash
# View application logs
tail -f logs/socio.log

# View Docker logs
docker-compose logs -f app
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Use meaningful variable names
- Add comments for complex logic
- Write unit tests for new features

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Neelesh Kurmi**
- GitHub: [@Neeleshkurmi](https://github.com/Neeleshkurmi)
- Email: neelesh@example.com
- LinkedIn: [Neelesh Kurmi](https://linkedin.com/in/neeleshkurmi)

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- Open source community for various libraries
- Contributors and testers

## 📞 Support

For support, email neelesh@example.com or open an issue in the GitHub repository.

---

⭐ **Star this repo** if you find it helpful!

🔄 **Check for updates** regularly as new features are added frequently.

🐛 **Report bugs** through GitHub issues for quick resolution.