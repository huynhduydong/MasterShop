# Discovery Server

Discovery Server là một service trung tâm để đăng ký và khám phá các microservice trong hệ thống MasterShop.

## Tính năng

- Đăng ký các service khi chúng khởi động
- Hủy đăng ký các service khi chúng dừng
- Cung cấp thông tin về các service đang chạy
- Giám sát trạng thái của các service

## Cách sử dụng

### Yêu cầu

- Java 17+
- Maven
- Config Server đang chạy

### Chạy

```bash
mvn spring-boot:run
```

### Truy cập

Discovery Server chạy trên cổng 8761 theo mặc định. Bạn có thể truy cập dashboard của Eureka bằng cách sử dụng URL sau:

```
http://localhost:8761
```

## Kiến thức về Service Discovery

Service Discovery là một phần quan trọng của kiến trúc microservice, cho phép các service tìm và giao tiếp với nhau mà không cần biết trước địa chỉ IP hoặc cổng của nhau.

### Lợi ích

1. **Tự động khám phá**: Các service có thể tự động tìm thấy nhau mà không cần cấu hình cứng
2. **Cân bằng tải**: Hỗ trợ cân bằng tải giữa các instance của cùng một service
3. **Khả năng chịu lỗi**: Tự động loại bỏ các instance không khả dụng
4. **Mở rộng quy mô**: Dễ dàng thêm hoặc xóa instance của một service

### Các khái niệm chính

- **Service Registry**: Cơ sở dữ liệu trung tâm lưu trữ thông tin về các service
- **Service Registration**: Quá trình một service đăng ký với registry
- **Service Discovery**: Quá trình một service tìm kiếm service khác
- **Health Check**: Kiểm tra định kỳ để đảm bảo các service vẫn hoạt động

### Eureka Server

Eureka Server là một triển khai của Service Registry trong Spring Cloud Netflix. Nó cung cấp một REST API để đăng ký và khám phá các service, cũng như một dashboard web để giám sát trạng thái của các service.

### Eureka Client

 