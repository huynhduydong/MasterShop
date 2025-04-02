# MasterShop Microservices

Dự án này là phiên bản microservice của ứng dụng MasterShop, một nền tảng thương mại điện tử.

## Kiến trúc tổng thể

![Microservice Architecture](https://i.imgur.com/JGvzXYa.png)

## Các service

### Infrastructure Services

1. **API Gateway**
   - Điểm vào duy nhất cho client
   - Xử lý routing, load balancing, authentication
   - Công nghệ: Spring Cloud Gateway

2. **Config Server**
   - Quản lý cấu hình tập trung cho tất cả các service
   - Công nghệ: Spring Cloud Config

3. **Discovery Server**
   - Đăng ký và khám phá các service
   - Công nghệ: Spring Cloud Netflix Eureka

### Core Services

4. **Auth Service**
   - Xác thực và phân quyền
   - Quản lý OAuth2, JWT
   - Cung cấp API cho việc đăng nhập, đăng ký, quản lý token

5. **User Service**
   - Quản lý thông tin người dùng
   - Quản lý địa chỉ
   - Quản lý vai trò và phân quyền

6. **Product Service**
   - Quản lý sản phẩm
   - Quản lý danh mục
   - Quản lý đánh giá sản phẩm
   - Quản lý thông số kỹ thuật và tùy chọn sản phẩm

7. **Order Service**
   - Quản lý đơn hàng
   - Xử lý thanh toán
   - Theo dõi trạng thái đơn hàng

8. **Cart Service**
   - Quản lý giỏ hàng
   - Lưu trữ thông tin giỏ hàng tạm thời
   - Sử dụng Redis

9. **Notification Service**
   - Gửi thông báo
   - Quản lý trạng thái thông báo

## Công nghệ sử dụng

- **Framework**: Spring Boot, Spring Cloud
- **Database**: MySQL, Redis
- **Message Broker**: Kafka/RabbitMQ
- **Container**: Docker
- **Orchestration**: Kubernetes
- **CI/CD**: GitHub Actions/GitLab CI
- **Monitoring**: Prometheus, Grafana
- **Distributed Tracing**: Zipkin, Spring Cloud Sleuth

## Cách chạy dự án

### Yêu cầu
- Java 17+
- Maven
- Docker và Docker Compose
- Kubernetes (tùy chọn)

### Các bước chạy
1. Clone repository
2. Chạy Config Server
3. Chạy Discovery Server
4. Chạy các service khác
5. Truy cập API Gateway

## Tài liệu API

Mỗi service đều có Swagger UI để tài liệu hóa API:
- Auth Service: http://localhost:8081/swagger-ui.html
- User Service: http://localhost:8082/swagger-ui.html
- Product Service: http://localhost:8083/swagger-ui.html
- Order Service: http://localhost:8084/swagger-ui.html
- Cart Service: http://localhost:8085/swagger-ui.html
- Notification Service: http://localhost:8086/swagger-ui.html

## Môi trường

- **Development**: localhost
- **Testing**: Docker Compose
- **Production**: Kubernetes 