# Tribal Handicrafts Platform - Backend

Complete Spring Boot backend for the Tribal Handicrafts Platform e-commerce application.

## Overview

This is a fully-featured Spring Boot backend API for managing:
- User authentication and authorization (JWT)
- Product catalog management
- Order processing
- Review and rating system
- Role-based access control

## Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Documentation**: Swagger/OpenAPI 3.0
- **ORM**: JPA/Hibernate

## Prerequisites

- Java 17
- Maven 3.8+
- MySQL 8.0+
- Postman (optional, for testing APIs)

## Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd backend
```

### 2. Configure MySQL Database
```sql
CREATE DATABASE handicrafts_db;
```

### 3. Update Database Configuration
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/handicrafts_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Generate JWT Secret
Update the JWT secret key in `application.properties`:
```properties
jwt.secret=your-secure-secret-key-here
```

### 5. Build and Run
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

The application will start at `http://localhost:8080/api`

## API Documentation

Swagger UI is available at: `http://localhost:8080/api/swagger-ui.html`

API Base URL: `http://localhost:8080/api`

### Authentication Endpoints

#### Register User
```
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "CUSTOMER"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "success": true,
  "message": "User logged in successfully",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "...",
    "userId": 1,
    "email": "john@example.com",
    "name": "John Doe",
    "role": "CUSTOMER"
  }
}
```

### Product Endpoints

#### Get All Products
```
GET /api/products
```

#### Create Product (Artisan only)
```
POST /api/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Handmade Rug",
  "description": "Beautiful handcrafted rug",
  "price": 150.00,
  "quantity": 10,
  "category": "Home Decor",
  "material": "Wool",
  "imageUrl": "https://..."
}
```

#### Get Product by ID
```
GET /api/products/{id}
```

#### Get Products by Category
```
GET /api/products/category/{category}
```

#### Search Products
```
GET /api/products/search?keyword=rug
```

#### Update Product (Artisan only)
```
PUT /api/products/{id}
Authorization: Bearer <token>
```

#### Delete Product (Artisan only)
```
DELETE /api/products/{id}
Authorization: Bearer <token>
```

### Order Endpoints

#### Place Order
```
POST /api/orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "shippingAddress": "123 Main St, City, State",
  "notes": "Please deliver carefully"
}
```

#### Get User Orders
```
GET /api/orders/user/{userId}
Authorization: Bearer <token>
```

#### Get Order by ID
```
GET /api/orders/{id}
Authorization: Bearer <token>
```

#### Update Order Status (Admin only)
```
PUT /api/orders/{id}/status?status=SHIPPED
Authorization: Bearer <token>
```

### Review Endpoints

#### Add Review
```
POST /api/reviews
Authorization: Bearer <token>
Content-Type: application/json

{
  "productId": 1,
  "rating": 5,
  "comment": "Excellent product!"
}
```

#### Get Product Reviews
```
GET /api/reviews/product/{productId}
```

#### Get User Reviews
```
GET /api/reviews/user/{userId}
```

#### Update Review
```
PUT /api/reviews/{id}
Authorization: Bearer <token>
```

#### Delete Review
```
DELETE /api/reviews/{id}
Authorization: Bearer <token>
```

### User Endpoints

#### Get User by ID
```
GET /api/users/{id}
Authorization: Bearer <token>
```

#### Update User Profile
```
PUT /api/users/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Jane Doe",
  "phoneNumber": "1234567890",
  "address": "456 Oak Ave",
  "city": "New York"
}
```

### Admin Endpoints

#### Get All Users
```
GET /api/admin/users
Authorization: Bearer <token>
```

#### Deactivate User
```
POST /api/admin/users/{id}/deactivate
Authorization: Bearer <token>
```

#### Get Orders by Status
```
GET /api/admin/orders/status/PENDING
Authorization: Bearer <token>
```

#### Update Order Status
```
PUT /api/admin/orders/{id}/status?status=DELIVERED
Authorization: Bearer <token>
```

## Project Structure

```
com.project.handicrafts.backend/
├── controller/          # REST Controllers
├── service/             # Business Logic
├── repository/          # Data Access Layer
├── entity/              # JPA Entities
├── dto/                 # Data Transfer Objects
├── security/            # JWT & Security
├── config/              # Configuration Classes
├── exception/           # Exception Handling
└── util/                # Utility Classes
```

## Key Features

✅ JWT-based Authentication
✅ Role-based Authorization (ADMIN, ARTISAN, CUSTOMER, CONSULTANT)
✅ User Registration & Login
✅ Product CRUD Operations
✅ Order Management
✅ Review System
✅ Admin Dashboard APIs
✅ Comprehensive Error Handling
✅ CORS Configuration for Frontend
✅ Swagger/OpenAPI Documentation
✅ AOP Logging
✅ Database Auditing
✅ Input Validation
✅ ModelMapper for DTOs

## Roles & Permissions

### CUSTOMER
- View products
- Create orders
- Leave reviews

### ARTISAN
- Create and manage products
- View orders
- Leave reviews

### CONSULTANT
- View all data
- Provide consultation

### ADMIN
- Full system access
- Manage users
- Manage products
- Manage orders
- View statistics

## Database Schema

### Users Table
- id, email, name, password, role, profileImageUrl, phoneNumber, address, city, state, zipCode, active, emailVerified, createdAt, updatedAt

### Products Table
- id, name, description, price, quantity, imageUrl, category, material, dimensions, rating, reviewCount, active, user_id, createdAt, updatedAt

### Orders Table
- id, user_id, totalAmount, status, shippingAddress, notes, createdAt, updatedAt, deliveredAt

### OrderItems Table
- id, order_id, product_id, quantity, priceAtPurchase, createdAt

### Reviews Table
- id, user_id, product_id, rating, comment, verified, createdAt, updatedAt

## Error Handling

The API returns standardized error responses:

```json
{
  "success": false,
  "message": "Error description",
  "timestamp": 1234567890
}
```

## Security

- ✅ BCrypt password encryption
- ✅ JWT token validation
- ✅ CORS configuration
- ✅ Role-based method security
- ✅ Input validation
- ✅ SQL injection prevention (JPA)

## Testing

### Using Postman
1. Register a user
2. Copy the token from response
3. Set Authorization header: `Bearer <token>`
4. Test endpoints

### Sample Data
The database is auto-populated with table structures. Add test data manually or create database initialization scripts.

## Logging

- SLF4J + Logback
- Log file: `logs/application.log`
- Log levels: DEBUG (dev), INFO (prod)

## Environment Variables

Create `.env` file:
```
DB_URL=jdbc:mysql://localhost:3306/handicrafts_db
DB_USERNAME=root
DB_PASSWORD=password
JWT_SECRET=your-secret-key
```

## Frontend Integration

The backend is configured to work with React frontend at `http://localhost:3000`

### CORS Configuration
- Allowed Origins: http://localhost:3000, http://localhost:3001
- Allowed Methods: GET, POST, PUT, DELETE, OPTIONS, PATCH
- Allowed Headers: *

## Deployment

### Docker
```bash
# Build Docker image
docker build -t handicrafts-backend .

# Run container
docker run -p 8080:8080 handicrafts-backend
```

### Production Checklist
- [ ] Update JWT secret key
- [ ] Configure production database
- [ ] Set environment variables
- [ ] Enable HTTPS
- [ ] Configure firewall rules
- [ ] Set up monitoring
- [ ] Enable logging persistence

## Troubleshooting

### Issue: JWT token not recognized
**Solution**: Ensure the JWT secret key in application.properties matches the key used to generate tokens

### Issue: CORS errors
**Solution**: Check CORS configuration in CorsConfig.java and ensure frontend URL is whitelisted

### Issue: Database connection failed
**Solution**: Verify MySQL is running and connection parameters are correct

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## License

This project is licensed under the MIT License.

## Support

For issues and questions, contact: support@handicrafts.com

## Changelog

### Version 1.0.0
- Initial release
- Complete authentication system
- Product management
- Order processing
- Review system
- Admin dashboard
"# HandiCrafts-Backend" 
