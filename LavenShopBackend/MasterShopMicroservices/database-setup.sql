-- Script tạo cơ sở dữ liệu cho MasterShop Microservices
-- Hỗ trợ tiếng Việt với UTF-8

-- Xóa cơ sở dữ liệu nếu đã tồn tại
DROP DATABASE IF EXISTS mastershop_auth;
DROP DATABASE IF EXISTS mastershop_user;
DROP DATABASE IF EXISTS mastershop_product;
DROP DATABASE IF EXISTS mastershop_order;
DROP DATABASE IF EXISTS mastershop_cart;
DROP DATABASE IF EXISTS mastershop_notification;

-- Tạo cơ sở dữ liệu cho Auth Service
CREATE DATABASE mastershop_auth
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Tạo cơ sở dữ liệu cho User Service
CREATE DATABASE mastershop_user
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Tạo cơ sở dữ liệu cho Product Service
CREATE DATABASE mastershop_product
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Tạo cơ sở dữ liệu cho Order Service
CREATE DATABASE mastershop_order
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Tạo cơ sở dữ liệu cho Cart Service
CREATE DATABASE mastershop_cart
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Tạo cơ sở dữ liệu cho Notification Service
CREATE DATABASE mastershop_notification
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Cấp quyền cho người dùng root
GRANT ALL PRIVILEGES ON mastershop_auth.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON mastershop_user.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON mastershop_product.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON mastershop_order.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON mastershop_cart.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON mastershop_notification.* TO 'root'@'localhost';

-- Áp dụng các thay đổi quyền
FLUSH PRIVILEGES;

-- Hiển thị danh sách cơ sở dữ liệu đã tạo
SHOW DATABASES LIKE 'mastershop_%'; 