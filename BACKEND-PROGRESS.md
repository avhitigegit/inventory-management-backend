# Backend Development Progress
## Hardware Stock Management System

---

## Overall Progress

| Area                        | Status      | Progress  |
|-----------------------------|-------------|-----------|
| Road Map (Steps 1–17)       | 16/17 done  | 94%       |
| Core Business Features      | Complete    | 100%      |
| Production-Ready Features   | Partial     | 40%       |
| **Overall Backend**         |             | **~70%**  |

---

## Files Built So Far — 87 Java Files

| Layer              | Files                                                                                     | Count |
|--------------------|-------------------------------------------------------------------------------------------|-------|
| Main App           | HardwareManagementApplication                                                             | 1     |
| Enums              | Role, PaymentType, AdjustmentType                                                         | 3     |
| Entities           | User, Category, Supplier, Customer, Product, Sale, SaleItem, Purchase, PurchaseItem, StockAdjustment | 10    |
| Repositories       | UserRepository, CategoryRepository, SupplierRepository, CustomerRepository, ProductRepository, SaleRepository, SaleItemRepository, PurchaseRepository, PurchaseItemRepository, StockAdjustmentRepository | 10    |
| Request DTOs       | LoginRequest, RegisterRequest, CategoryRequest, SupplierRequest, CustomerRequest, ProductRequest, SaleRequest, SaleItemRequest, PurchaseRequest, PurchaseItemRequest, StockAdjustmentRequest | 11    |
| Response DTOs      | AuthResponse, CategoryResponse, SupplierResponse, CustomerResponse, ProductResponse, SaleResponse, SaleItemResponse, PurchaseResponse, PurchaseItemResponse, LowStockResponse, StockAdjustmentResponse, DashboardResponse, TopProductResponse | 13    |
| Security           | JwtUtil, JwtAuthFilter, CustomUserDetailsService                                          | 3     |
| Config             | SecurityConfig, CorsConfig, DataSeeder                                                    | 3     |
| Exceptions         | ResourceNotFoundException, InsufficientStockException, DuplicateEntryException, ErrorResponse, GlobalExceptionHandler | 5     |
| Service Interfaces | AuthService, CategoryService, SupplierService, CustomerService, ProductService, PurchaseService, SaleService, StockAdjustmentService, ReportService | 9     |
| Service Impl       | AuthServiceImpl, CategoryServiceImpl, SupplierServiceImpl, CustomerServiceImpl, ProductServiceImpl, PurchaseServiceImpl, SaleServiceImpl, StockAdjustmentServiceImpl, ReportServiceImpl | 9     |
| Controllers        | AuthController, CategoryController, SupplierController, CustomerController, ProductController, PurchaseController, SaleController, StockAdjustmentController, ReportController | 9     |
| **Total**          |                                                                                           | **87** |

---

## Road Map Steps — Status

### Completed Steps

| Step | Description                         | Status |
|------|-------------------------------------|--------|
| 1    | Spring Boot project setup           | ✅ Done |
| 2    | JWT dependencies in pom.xml         | ✅ Done |
| 3    | PostgreSQL database setup           | ✅ Done |
| 4    | application.properties configured   | ✅ Done |
| 5    | Folder / package structure created  | ✅ Done |
| 6    | Enum classes                        | ✅ Done |
| 7    | Entity classes (10 entities)        | ✅ Done |
| 8    | Repository interfaces (10 repos)    | ✅ Done |
| 9    | DTO classes (24 DTOs)               | ✅ Done |
| 10   | JWT security classes                | ✅ Done |
| 11   | Security configuration              | ✅ Done |
| 12   | Exception classes + global handler  | ✅ Done |
| 13   | Service layer (9 interfaces + 9 impl) | ✅ Done |
| 14   | Controller layer (9 controllers)    | ✅ Done |
| 15   | CORS configuration                  | ✅ Done |
| 16   | Data seeder (default admin user)    | ✅ Done |

### Pending Step

| Step | Description                         | Status  |
|------|-------------------------------------|---------|
| 17   | Test all APIs with Postman          | ⬜ Pending |

---

## What Still Needs to Be Done for Production

### 1. Postman API Testing (Step 17)
Test all endpoints in this order as per the road map:
- [ ] Register admin user
- [ ] Login and get JWT token
- [ ] Create a category
- [ ] Create a supplier
- [ ] Create a product
- [ ] Create a purchase (GRN) — stock should increase
- [ ] Create a sale — stock should decrease
- [ ] Check low stock products
- [ ] Check dashboard report

---

### 2. User Management API
Currently users can only register. There is no way to manage users after creation.

- [ ] `GET  /api/users` — list all users (ADMIN only)
- [ ] `PUT  /api/users/{id}` — update user role (ADMIN only)
- [ ] `PUT  /api/users/{id}/status` — activate / deactivate a user (ADMIN only)
- [ ] `PUT  /api/users/change-password` — allow any user to change their own password

**Branch suggestion:** `feature/user-management`

---

### 3. Customer Credit Settlement
Currently customer credit balance only increases when a credit sale is made.
There is no way to record a payment from a customer to reduce their balance.

- [ ] `POST /api/customers/{id}/payment` — record customer payment, reduce credit balance
- [ ] Add `CustomerPaymentRequest` DTO — amount field
- [ ] Update `CustomerService` and `CustomerController`

**Branch suggestion:** `feature/customer-payment`

---

### 4. Supplier Payment Recording
Same issue as customer credit — supplier credit balance is never reduced.

- [ ] `POST /api/suppliers/{id}/payment` — record payment to supplier, reduce their credit balance
- [ ] Add `SupplierPaymentRequest` DTO — amount field
- [ ] Update `SupplierService` and `SupplierController`

**Branch suggestion:** `feature/supplier-payment`

---

### 5. Pagination and Search
Currently all list endpoints return every record. This will be very slow
with thousands of products or sales.

- [ ] Add `Pageable` support to `GET /api/products` — page, size, sort params
- [ ] Add `Pageable` support to `GET /api/sales`
- [ ] Add `Pageable` support to `GET /api/purchases`
- [ ] Add product search by name or SKU — `GET /api/products?search=nail`
- [ ] Add sales filter by customer — `GET /api/sales?customerId=1`

**Branch suggestion:** `feature/pagination-search`

---

### 6. API Documentation (Swagger / OpenAPI)
The frontend developer needs to know all available endpoints, request formats,
and response formats. Swagger generates this automatically.

- [ ] Add `springdoc-openapi-starter-webmvc-ui` dependency to pom.xml
- [ ] Add `@Operation` and `@Tag` annotations to controllers
- [ ] Access at `http://localhost:8080/swagger-ui.html` after setup

**Branch suggestion:** `feature/swagger-docs`

---

### 7. Spring Environment Profiles
Currently `application.properties` has hardcoded credentials.
Production and development should use different settings.

- [ ] Create `application-dev.properties` — local dev DB credentials, show SQL
- [ ] Create `application-prod.properties` — production DB URL, hide SQL, secure JWT secret
- [ ] Update `application.properties` to set active profile — `spring.profiles.active=dev`

**Branch suggestion:** `feature/env-profiles`

---

### 8. Unit and Integration Tests
Currently there are no tests. Tests are important to catch bugs before they
reach production.

- [ ] Unit tests for `SaleServiceImpl` — stock validation logic
- [ ] Unit tests for `AuthServiceImpl` — login and register logic
- [ ] Unit tests for `ProductServiceImpl` — duplicate SKU / barcode checks
- [ ] Integration test — full purchase flow (GRN increases stock)
- [ ] Integration test — full sale flow (sale decreases stock, credit updates)

**Branch suggestion:** `feature/tests`

---

## API Endpoints Summary — All Available Now

| Method | Endpoint                              | Role Required              |
|--------|---------------------------------------|----------------------------|
| POST   | /api/auth/login                       | Public                     |
| POST   | /api/auth/register                    | Public                     |
| GET    | /api/categories                       | Any logged-in user         |
| POST   | /api/categories                       | ADMIN, OWNER               |
| PUT    | /api/categories/{id}                  | ADMIN, OWNER               |
| DELETE | /api/categories/{id}                  | ADMIN, OWNER               |
| GET    | /api/suppliers                        | Any logged-in user         |
| POST   | /api/suppliers                        | ADMIN, OWNER               |
| PUT    | /api/suppliers/{id}                   | ADMIN, OWNER               |
| DELETE | /api/suppliers/{id}                   | ADMIN only                 |
| GET    | /api/customers                        | Any logged-in user         |
| GET    | /api/customers/credit                 | Any logged-in user         |
| POST   | /api/customers                        | ADMIN, OWNER, CASHIER      |
| PUT    | /api/customers/{id}                   | ADMIN, OWNER, CASHIER      |
| GET    | /api/products                         | Any logged-in user         |
| GET    | /api/products/{id}                    | Any logged-in user         |
| GET    | /api/products/barcode/{barcode}       | Any logged-in user         |
| GET    | /api/products/low-stock               | Any logged-in user         |
| POST   | /api/products                         | ADMIN, OWNER, STORE_KEEPER |
| PUT    | /api/products/{id}                    | ADMIN, OWNER, STORE_KEEPER |
| DELETE | /api/products/{id}                    | ADMIN, OWNER               |
| GET    | /api/purchases                        | Any logged-in user         |
| GET    | /api/purchases/{id}                   | Any logged-in user         |
| POST   | /api/purchases                        | ADMIN, OWNER, STORE_KEEPER |
| GET    | /api/sales                            | Any logged-in user         |
| GET    | /api/sales/{id}                       | Any logged-in user         |
| GET    | /api/sales/report?start=&end=         | Any logged-in user         |
| POST   | /api/sales                            | ADMIN, OWNER, CASHIER      |
| GET    | /api/stock-adjustments                | Any logged-in user         |
| POST   | /api/stock-adjustments                | ADMIN, OWNER, STORE_KEEPER |
| GET    | /api/reports/dashboard                | ADMIN, OWNER               |
| GET    | /api/reports/daily                    | ADMIN, OWNER               |
| GET    | /api/reports/monthly                  | ADMIN, OWNER               |
| GET    | /api/reports/low-stock                | ADMIN, OWNER               |
| GET    | /api/reports/top-products             | ADMIN, OWNER               |

---

## Recommended Next Steps (in order)

1. **Step 17** — Test all APIs with Postman on `dev` branch
2. **feature/user-management** — User management endpoints
3. **feature/customer-payment** — Customer credit settlement
4. **feature/supplier-payment** — Supplier payment recording
5. **feature/swagger-docs** — API documentation
6. **feature/env-profiles** — Dev / prod configuration separation
7. **feature/pagination-search** — Pagination and search
8. **feature/tests** — Unit and integration tests

---

*Last updated: 2026-05-10*
