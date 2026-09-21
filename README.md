# GraphQL Product API

API quản lý `Product`, `Category`, `User` và các quan hệ nhiều-nhiều bằng Spring Boot, Spring GraphQL, Spring Data JPA và MySQL.

## Chạy project

1. Cài MySQL và tạo database (ứng dụng tự tạo database nếu user có quyền):
   `mysql -u root -p -e "CREATE DATABASE graphql_store;"`
2. Cấu hình biến môi trường `DB_USERNAME`, `DB_PASSWORD` nếu cần.
3. Chạy: `mvn spring-boot:run`
4. Mở giao diện AJAX tại http://localhost:8080 và GraphiQL tại http://localhost:8080/graphiql.

## Chức năng GraphQL

- Query toàn bộ sản phẩm theo giá tăng dần: `{ products { id title price category { name } } }`
- Lấy sản phẩm theo category: `{ productsByCategory(categoryId: 1) { title price } }`
- CRUD Product, Category và User; User có thể gán nhiều Category thông qua các mutation trong `src/main/resources/graphql/schema.graphqls`.
- Giao diện AJAX hỗ trợ thêm/xóa Product và Category.
