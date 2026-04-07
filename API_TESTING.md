# API Testing Collection

This document contains curl commands and examples for testing all API endpoints.

## Base URL

`http://localhost:8080`

## Swagger / OpenAPI

OpenAPI JSON:

```bash
curl -X GET http://localhost:8080/api/v3/api-docs
```

Swagger UI:

`http://localhost:8080/api/swagger-ui.html`

## Authentication Endpoints

### 1. Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "CUSTOMER"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

**Response:**
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

**Save token for subsequent requests:**
```bash
export TOKEN="your-jwt-token-here"
```

## Product Endpoints

### 3. Get All Products
```bash
curl -X GET http://localhost:8080/api/products \
  -H "Content-Type: application/json"
```

### 4. Get Product by ID
```bash
curl -X GET http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json"
```

### 5. Get Products by Category
```bash
curl -X GET http://localhost:8080/api/products/category/Home%20Decor \
  -H "Content-Type: application/json"
```

### 6. Search Products
```bash
curl -X GET "http://localhost:8080/api/products/search?keyword=rug" \
  -H "Content-Type: application/json"
```

### 7. Get Products by Price Range
```bash
curl -X GET "http://localhost:8080/api/products/price-range?minPrice=1000&maxPrice=5000" \
  -H "Content-Type: application/json"
```

### 8. Create Product (Artisan Only)
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Handmade Rug",
    "description": "Beautiful handcrafted rug",
    "price": 5000,
    "quantity": 10,
    "category": "Home Decor",
    "material": "Wool",
    "dimensions": "100x150 cm",
    "imageUrl": "https://example.com/rug.jpg"
  }'
```

### 9. Update Product (Artisan Only)
```bash
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "price": 5500,
    "quantity": 8
  }'
```

### 10. Delete Product (Artisan Only)
```bash
curl -X DELETE http://localhost:8080/api/products/1 \
  -H "Authorization: Bearer $TOKEN"
```

## Order Endpoints

### 11. Get All Products (For adding to cart)
```bash
curl -X GET http://localhost:8080/api/products
```

### 12. Place Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "shippingAddress": "123 Main Street, Mumbai, MH 400001",
    "notes": "Please deliver carefully"
  }' \
  -d '[
    {
      "orderId": 1,
      "productId": 1,
      "quantity": 2,
      "priceAtPurchase": 3500
    }
  ]'
```

### 13. Get User Orders
```bash
curl -X GET http://localhost:8080/api/orders/user/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 14. Get Order by ID
```bash
curl -X GET http://localhost:8080/api/orders/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 15. Get Order Items
```bash
curl -X GET http://localhost:8080/api/orders/1/items \
  -H "Authorization: Bearer $TOKEN"
```

### 16. Update Order Status (Admin Only)
```bash
curl -X PUT "http://localhost:8080/api/orders/1/status?status=SHIPPED" \
  -H "Authorization: Bearer $TOKEN"
```

### 17. Get Orders by Status
```bash
curl -X GET http://localhost:8080/api/orders/status/PENDING \
  -H "Authorization: Bearer $TOKEN"
```

## Review Endpoints

### 18. Add Review
```bash
curl -X POST http://localhost:8080/api/reviews \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "productId": 1,
    "rating": 5,
    "comment": "Excellent product! Highly recommended."
  }'
```

### 19. Get Product Reviews
```bash
curl -X GET http://localhost:8080/api/reviews/product/1
```

### 20. Get User Reviews
```bash
curl -X GET http://localhost:8080/api/reviews/user/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 21. Update Review
```bash
curl -X PUT http://localhost:8080/api/reviews/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "rating": 4,
    "comment": "Good product, delivery was slow."
  }'
```

### 22. Delete Review
```bash
curl -X DELETE http://localhost:8080/api/reviews/1 \
  -H "Authorization: Bearer $TOKEN"
```

## User Endpoints

### 23. Get User by ID
```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 24. Get User by Email
```bash
curl -X GET http://localhost:8080/api/users/email/john@example.com \
  -H "Authorization: Bearer $TOKEN"
```

### 25. Update User Profile
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Jane Doe",
    "phoneNumber": "9876543210",
    "address": "456 Oak Avenue",
    "city": "New York",
    "state": "NY"
  }'
```

### 26. Get All Users (Admin Only)
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer $TOKEN"
```

### 27. Get Users by Role
```bash
curl -X GET http://localhost:8080/api/users/role/ARTISAN \
  -H "Authorization: Bearer $TOKEN"
```

### 28. Activate User (Admin Only)
```bash
curl -X POST http://localhost:8080/api/users/1/activate \
  -H "Authorization: Bearer $TOKEN"
```

### 29. Deactivate User (Admin Only)
```bash
curl -X POST http://localhost:8080/api/users/1/deactivate \
  -H "Authorization: Bearer $TOKEN"
```

## Admin Endpoints

### 30. Get All Users (Admin)
```bash
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer $TOKEN"
```

### 31. Delete User (Admin Only)
```bash
curl -X DELETE http://localhost:8080/api/admin/users/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 32. Get Pending Orders (Admin)
```bash
curl -X GET http://localhost:8080/api/admin/orders/status/PENDING \
  -H "Authorization: Bearer $TOKEN"
```

### 33. Update Order Status (Admin)
```bash
curl -X PUT "http://localhost:8080/api/admin/orders/1/status?status=DELIVERED" \
  -H "Authorization: Bearer $TOKEN"
```

### 34. Get Pending Orders Count
```bash
curl -X GET http://localhost:8080/api/admin/stats/orders/pending \
  -H "Authorization: Bearer $TOKEN"
```

## File Upload Endpoints

### 35. Upload Product Image
```bash
curl -X POST http://localhost:8080/api/upload/product-image \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@/path/to/image.jpg"
```

### 36. Delete Product Image
```bash
curl -X DELETE "http://localhost:8080/api/upload/product-image?filePath=uploads/products/filename.jpg" \
  -H "Authorization: Bearer $TOKEN"
```

## Response Format

All API responses follow this format:

### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { /* actual data */ },
  "timestamp": 1704067200000
}
```

### Error Response
```json
{
  "success": false,
  "message": "Error description",
  "timestamp": 1704067200000
}
```

## Test User Credentials

Use these credentials for testing (password: password123):

- **Admin**: admin@handicrafts.com
- **Artisan 1**: artisan1@handicrafts.com
- **Artisan 2**: artisan2@handicrafts.com
- **Customer 1**: customer1@handicrafts.com
- **Customer 2**: customer2@handicrafts.com
- **Consultant**: consultant@handicrafts.com

## Test Data

Run `sample-data.sql` to populate test data:

```bash
mysql -u root -p handicrafts_db < sample-data.sql
```

## Postman Collection

Import the included Postman collection file for easier testing:
1. Open Postman
2. File → Import → Select the collection JSON file
3. Environment variables will be automatically loaded
4. Start testing!

## Common Issues

**401 Unauthorized**: Token expired or missing. Re-login and get a new token.

**403 Forbidden**: You don't have permission for this action. Check your role.

**404 Not Found**: Resource doesn't exist. Check the ID or endpoint URL.

**400 Bad Request**: Invalid input data. Check request body and parameters.

**500 Internal Server Error**: Server error. Check server logs.
