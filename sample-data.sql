-- Tribal Handicrafts Platform - Sample Data Insert Script
-- Author: Development Team
-- Description: Sample data for testing and demonstration

USE handicrafts_db;

-- Insert sample users
INSERT INTO users (email, name, password, role, phone_number, city, state, active, email_verified, created_at, updated_at) 
VALUES 
('admin@handicrafts.com', 'Admin User', '$2a$10$dXJ3SW6G7P50eS3BQi09k.7aPM9f0WKwXzzJX8OFIvKt1M7dSPgDm', 'ADMIN', '1234567890', 'New York', 'NY', true, true, 1704067200000, 1704067200000),
('artisan1@handicrafts.com', 'Artisan One', '$2a$10$dXJ3SW6G7P50eS3BQi09k.7aPM9f0WKwXzzJX8OFIvKt1M7dSPgDm', 'ARTISAN', '2234567890', 'Jaipur', 'Rajasthan', true, true, 1704067200000, 1704067200000),
('artisan2@handicrafts.com', 'Artisan Two', '$2a$10$dXJ3SW6G7P50eS3BQi09k.7aPM9f0WKwXzzJX8OFIvKt1M7dSPgDm', 'ARTISAN', '3234567890', 'Varanasi', 'UP', true, true, 1704067200000, 1704067200000),
('customer1@handicrafts.com', 'Customer One', '$2a$10$dXJ3SW6G7P50eS3BQi09k.7aPM9f0WKwXzzJX8OFIvKt1M7dSPgDm', 'CUSTOMER', '4234567890', 'Mumbai', 'MH', true, true, 1704067200000, 1704067200000),
('customer2@handicrafts.com', 'Customer Two', '$2a$10$dXJ3SW6G7P50eS3BQi09k.7aPM9f0WKwXzzJX8OFIvKt1M7dSPgDm', 'CUSTOMER', '5234567890', 'Bangalore', 'KA', true, true, 1704067200000, 1704067200000),
('consultant@handicrafts.com', 'Consultant One', '$2a$10$dXJ3SW6G7P50eS3BQi09k.7aPM9f0WKwXzzJX8OFIvKt1M7dSPgDm', 'CONSULTANT', '6234567890', 'Delhi', 'DL', true, true, 1704067200000, 1704067200000);

-- Insert sample products (created by Artisan One - id=2)
INSERT INTO products (name, description, price, quantity, image_url, category, material, dimensions, rating, review_count, active, user_id, created_at, updated_at) 
VALUES 
('Handmade Ceramic Vase', 'Beautiful handcrafted ceramic vase with traditional patterns', 3500.00, 15, 'uploads/products/vase1.jpg', 'Home Decor', 'Ceramic', '25x15 cm', 5, 3, true, 2, 1704067200000, 1704067200000),
('Tribal Wooden Mask', 'Authentic tribal wooden mask with intricate carvings', 4500.00, 8, 'uploads/products/mask1.jpg', 'Art & Craft', 'Wood', '30x25 cm', 4, 2, true, 2, 1704067200000, 1704067200000),
('Beaded Textile Art', 'Colorful textile art with traditional beadwork', 2800.00, 20, 'uploads/products/textile1.jpg', 'Textiles', 'Cotton', '50x40 cm', 5, 4, true, 2, 1704067200000, 1704067200000);

-- Insert sample products (created by Artisan Two - id=3)
INSERT INTO products (name, description, price, quantity, image_url, category, material, dimensions, rating, review_count, active, user_id, created_at, updated_at) 
VALUES 
('Hand-woven Rug', 'Traditional hand-woven rug with authentic patterns', 8000.00, 5, 'uploads/products/rug1.jpg', 'Home Decor', 'Wool', '100x150 cm', 5, 5, true, 3, 1704067200000, 1704067200000),
('Wooden Jewelry Box', 'Carved wooden jewelry box with velvet lining', 3200.00, 12, 'uploads/products/box1.jpg', 'Home Decor', 'Wood', '25x20 cm', 4, 3, true, 3, 1704067200000, 1704067200000),
('Handmade Pottery Set', 'Set of 4 handmade ceramic plates', 2500.00, 18, 'uploads/products/pottery1.jpg', 'Kitchen', 'Ceramic', 'Various', 5, 2, true, 3, 1704067200000, 1704067200000);

-- Insert sample orders (from Customer One - id=4)
INSERT INTO orders (user_id, total_amount, status, shipping_address, notes, created_at, updated_at)
VALUES 
(4, 7300.00, 'DELIVERED', '123 Main Street, Mumbai, MH 400001', 'Delivered successfully', 1704067200000, 1704240000000),
(4, 3500.00, 'SHIPPED', '123 Main Street, Mumbai, MH 400001', 'In transit', 1704326400000, 1704412800000);

-- Insert order items for order 1
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase, created_at)
VALUES 
(1, 1, 2, 3500.00, 1704067200000),
(1, 2, 1, 4500.00, 1704067200000);

-- Insert order items for order 2
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase, created_at)
VALUES 
(2, 3, 1, 2800.00, 1704326400000),
(2, 5, 1, 3200.00, 1704326400000);

-- Insert sample reviews
INSERT INTO reviews (user_id, product_id, rating, comment, verified, created_at, updated_at)
VALUES 
(4, 1, 5, 'Excellent quality and beautiful design. Highly recommended!', true, 1704240000000, 1704240000000),
(4, 2, 4, 'Great mask, authentic tribal art. A bit fragile during shipping.', true, 1704240000000, 1704240000000),
(5, 1, 5, 'Perfect for my living room! Love the craftsmanship.', true, 1704412800000, 1704412800000),
(4, 4, 5, 'Beautiful wooden box, very well made!', true, 1704412800000, 1704412800000),
(5, 5, 4, 'Nice pottery set. Colors are vibrant and plates are durable.', true, 1704499200000, 1704499200000);

-- Verify inserts
SELECT 'Users Created:' as info;
SELECT COUNT(*) as total_users FROM users;

SELECT 'Products Created:' as info;
SELECT COUNT(*) as total_products FROM products;

SELECT 'Orders Created:' as info;
SELECT COUNT(*) as total_orders FROM orders;

SELECT 'Order Items Created:' as info;
SELECT COUNT(*) as total_order_items FROM order_items;

SELECT 'Reviews Created:' as info;
SELECT COUNT(*) as total_reviews FROM reviews;

-- Note: Password is 'password123' for all test users (hashed with BCrypt)
-- You can use these credentials to test the API
