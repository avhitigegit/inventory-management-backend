# 🏪 Inventory Management System — Backend

A robust and scalable backend system for hardware shop inventory management, built with **Spring Boot 3**, **Java 21**, and **PostgreSQL**. Designed specifically for small and medium hardware shops in Sri Lanka.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Security | Spring Security + JWT |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Build Tool | Maven |
| API Style | RESTful API |

---

## 📦 Features

- 🔐 **Authentication & Authorization** — JWT-based login with role-based access control
- 📦 **Product Management** — Full CRUD with SKU, barcode, pricing, and stock tracking
- 🗂️ **Category Management** — Organize products by category
- 🏭 **Supplier Management** — Track suppliers and credit balances
- 👤 **Customer Management** — Manage customers and credit accounts
- 📥 **GRN (Goods Received Note)** — Receive stock from suppliers, auto-update inventory
- 🧾 **POS / Billing** — Process sales, handle cash and credit payments, auto-deduct stock
- 📊 **Stock Adjustment** — Manual stock corrections with reason tracking
- 📈 **Reports** — Dashboard summary, daily sales, monthly sales, low stock alerts, top-selling products

---

## 👥 User Roles

| Role | Access |
|---|---|
| `ADMIN` | Full system access |
| `OWNER` | Reports, products, full view |
| `CASHIER` | POS billing, customers |
| `STORE_KEEPER` | GRN, stock management |

---

## 🗄️ Database Schema (Main Tables)

```
users, categories, suppliers, customers,
products, purchases, purchase_items,
sales, sale_items, stock_adjustments
```

---

## 📁 Project Structure

```
src/main/java/com/hardware/
├── controller/         # REST API endpoints
├── service/            # Business logic
│   └── impl/
├── repository/         # JPA repositories
├── entity/             # Database entities
├── dto/
│   ├── request/        # Incoming request payloads
│   └── response/       # Outgoing response payloads
├── security/           # JWT filter, UserDetailsService
├── config/             # Security config, CORS config
├── exception/          # Custom exceptions, global handler
├── util/               # Utility classes
└── mapper/             # Entity ↔ DTO mappers
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- PostgreSQL 14+
- IntelliJ IDEA (recommended)

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/inventory-management-backend.git
cd inventory-management-backend
```

### 2. Create PostgreSQL Database

```sql
CREATE DATABASE hardware_db;
CREATE USER hardware_user WITH PASSWORD 'yourpassword';
GRANT ALL PRIVILEGES ON DATABASE hardware_db TO hardware_user;
```

### 3. Configure application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hardware_db
spring.datasource.username=hardware_user
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_very_long_secret_key_minimum_256_bits
jwt.expiration=86400000

server.port=8080
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Server will start at: `http://localhost:8080`

---

## 🔑 Default Admin Login

On first run, the system auto-creates a default admin account:

```
Username: admin
Password: admin123
```

> ⚠️ Change the default password immediately after first login.

---

## 🌐 API Endpoints Overview

### Auth
```
POST   /api/auth/login
POST   /api/auth/register
```

### Products
```
GET    /api/products
POST   /api/products
PUT    /api/products/{id}
DELETE /api/products/{id}
GET    /api/products/low-stock
GET    /api/products/barcode/{barcode}
```

### Categories
```
GET    /api/categories
POST   /api/categories
PUT    /api/categories/{id}
DELETE /api/categories/{id}
```

### Suppliers
```
GET    /api/suppliers
POST   /api/suppliers
PUT    /api/suppliers/{id}
DELETE /api/suppliers/{id}
```

### Customers
```
GET    /api/customers
POST   /api/customers
PUT    /api/customers/{id}
GET    /api/customers/credit
```

### Purchases (GRN)
```
GET    /api/purchases
POST   /api/purchases
GET    /api/purchases/{id}
```

### Sales (POS)
```
GET    /api/sales
POST   /api/sales
GET    /api/sales/{id}
GET    /api/sales/report
```

### Stock Adjustments
```
POST   /api/stock-adjustments
GET    /api/stock-adjustments
```

### Reports
```
GET    /api/reports/dashboard
GET    /api/reports/daily
GET    /api/reports/monthly
GET    /api/reports/top-products
GET    /api/reports/low-stock
```

---

## 🔒 Security

- All endpoints except `/api/auth/**` require a valid JWT token
- Pass token in request header:
```
Authorization: Bearer <your_token>
```

---

## 🛠️ Development Phases

- [x] Phase 1 — Authentication, Products, Stock, Billing, Reports
- [ ] Phase 2 — Barcode support, GRN, Supplier & Customer credit
- [ ] Phase 3 — Multi-branch, Mobile app, WhatsApp integration

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License.

---

## 📞 Contact

For any queries related to this project, please open a GitHub issue.

---

> Built with ❤️ for Sri Lankan hardware shops
