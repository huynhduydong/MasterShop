# Config Server

Config Server là một service trung tâm để quản lý cấu hình cho tất cả các microservice trong hệ thống MasterShop.

## Tính năng

- Quản lý cấu hình tập trung cho tất cả các service
- Hỗ trợ mã hóa thông tin nhạy cảm
- Hỗ trợ refresh cấu hình runtime
- Lưu trữ cấu hình trong Git repository

## Cách sử dụng

### Yêu cầu

- Java 17+
- Maven
- Git

### Cấu hình

Trước khi chạy Config Server, bạn cần tạo một Git repository để lưu trữ cấu hình. Repository này nên có cấu trúc như sau:

```
mastershop-config/
├── application.yml (cấu hình chung cho tất cả các service)
├── auth-service/
│   └── auth-service.yml
├── user-service/
│   └── user-service.yml
├── product-service/
│   └── product-service.yml
├── order-service/
│   └── order-service.yml
├── cart-service/
│   └── cart-service.yml
└── notification-service/
    └── notification-service.yml
```

Sau đó, cập nhật `application.yml` của Config Server để trỏ đến Git repository của bạn:

```yaml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/yourusername/mastershop-config
          default-label: main
          search-paths: '{application}'
          clone-on-start: true
```

### Chạy

```bash
mvn spring-boot:run
```

### Truy cập

Config Server chạy trên cổng 8888 theo mặc định. Bạn có thể truy cập cấu hình của một service cụ thể bằng cách sử dụng URL sau:

```
http://localhost:8888/{service-name}/{profile}
```

Ví dụ:

```
http://localhost:8888/user-service/default
```

### Bảo mật

Config Server được bảo vệ bằng xác thực cơ bản. Thông tin đăng nhập mặc định là:

- Username: configuser
- Password: configpassword

Bạn nên thay đổi thông tin đăng nhập này trong môi trường sản xuất.

## Kiến thức về Spring Cloud Config

Spring Cloud Config cung cấp hỗ trợ phía server và client cho cấu hình ngoài trong một hệ thống phân tán. Với Config Server, bạn có một nơi trung tâm để quản lý các thuộc tính ngoài cho các ứng dụng trong tất cả các môi trường.

### Lợi ích

1. **Quản lý tập trung**: Tất cả cấu hình được lưu trữ và quản lý tại một nơi
2. **Phiên bản hóa**: Cấu hình được lưu trữ trong Git, cho phép phiên bản hóa và rollback
3. **Bảo mật**: Hỗ trợ mã hóa thông tin nhạy cảm
4. **Refresh runtime**: Cấu hình có thể được cập nhật mà không cần khởi động lại ứng dụng

### Các khái niệm chính

- **Config Server**: Service trung tâm lưu trữ cấu hình
- **Config Client**: Các service sử dụng cấu hình từ Config Server
- **Profile**: Môi trường cụ thể (dev, test, prod)
- **Label**: Phiên bản cụ thể của cấu hình (thường là branch hoặc tag trong Git) 