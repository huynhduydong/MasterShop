# API Gateway for MasterShop Microservices

API Gateway là thành phần trung tâm của hệ thống MasterShop Microservices, đóng vai trò là điểm vào duy nhất cho tất cả các yêu cầu từ client.

## Tính năng

- **Định tuyến**: Chuyển tiếp yêu cầu đến các microservice phù hợp
- **Xác thực**: Xác thực JWT cho các yêu cầu
- **Circuit Breaker**: Xử lý khi các service không khả dụng
- **Fallback**: Cung cấp phản hồi dự phòng khi service không phản hồi
- **Logging**: Ghi log các yêu cầu và phản hồi
- **CORS**: Cấu hình CORS cho các client từ các nguồn khác nhau

## Cài đặt và Chạy

### Yêu cầu

- Java 17+
- Maven 3.6+
- Eureka Server đang chạy (mặc định tại `http://localhost:8761`)

### Chạy ứng dụng

```bash
mvn clean install
mvn spring-boot:run
```

API Gateway sẽ chạy tại `http://localhost:8080`.

## Tối ưu hóa hiệu suất

### Tối ưu hóa JVM

Thêm các tham số JVM sau khi chạy ứng dụng:

```bash
java -Xms256m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -jar target/api-gateway-0.0.1-SNAPSHOT.jar
```

### Tối ưu hóa Maven

Sử dụng Maven Daemon để tăng tốc quá trình build:

```bash
# Cài đặt Maven Daemon
curl -fsSL https://raw.githubusercontent.com/mvndaemon/mvnd/master/install.sh | bash
# Sử dụng mvnd thay vì mvn
mvnd clean install
```

### Tối ưu hóa IDE

- **IntelliJ IDEA**: Tăng bộ nhớ được cấp phát trong file `idea.vmoptions`
- **Eclipse**: Tăng bộ nhớ trong file `eclipse.ini`
- **VS Code**: Sử dụng extension "Performance: Startup Profiler" để phân tích hiệu suất

### Tối ưu hóa cấu hình

- **Lazy Initialization**: Đã bật trong `application.properties` để giảm thời gian khởi động
- **Caching**: Đã cấu hình Caffeine cache để cải thiện hiệu suất
- **Logging**: Giảm mức độ logging trong môi trường production
- **Eureka Client**: Tối ưu hóa các khoảng thời gian fetch registry và renewal

### Giám sát hiệu suất

API Gateway đã được cấu hình để xuất metrics thông qua Actuator và Prometheus. Bạn có thể truy cập:

- `/actuator/health` - Kiểm tra trạng thái
- `/actuator/metrics` - Xem các metrics
- `/actuator/prometheus` - Endpoint cho Prometheus scraping

## Cấu trúc dự án

```
api-gateway/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── dong/
│   │   │           └── apigateway/
│   │   │               ├── config/       # Cấu hình
│   │   │               ├── controller/   # Controllers
│   │   │               ├── exception/    # Xử lý ngoại lệ
│   │   │               ├── filter/       # Filters
│   │   │               └── util/         # Tiện ích
│   │   └── resources/
│   │       ├── application.yml           # Cấu hình chính
│   │       └── application.properties    # Cấu hình bổ sung
│   └── test/                             # Unit tests
└── pom.xml                               # Maven configuration
```

## Troubleshooting

### Lỗi kết nối đến Eureka Server

Đảm bảo Eureka Server đang chạy tại `http://localhost:8761`. Nếu không, cập nhật `eureka.client.serviceUrl.defaultZone` trong `application.properties`.

### Lỗi OutOfMemoryError

Tăng bộ nhớ JVM bằng cách thêm tham số `-Xmx1g` khi chạy ứng dụng.

### Lỗi kết nối đến Redis

Nếu bạn sử dụng Redis cho Rate Limiting, đảm bảo Redis đang chạy và cấu hình đúng trong `application.yml`.

## Liên hệ

Nếu bạn có bất kỳ câu hỏi hoặc đề xuất nào, vui lòng liên hệ với chúng tôi tại [support@mastershop.com](mailto:support@mastershop.com). 