# 🛍️ E-Commerce Web Application (Under Development)

A full-stack **E-Commerce application** built using **Spring Boot, Spring Data JPA, Thymeleaf, Bootstrap, and MySQL**.  
This project is **currently under development** 🚧. New features like cart, checkout, and authentication are being implemented.

---

## ✨ Features

### 👩‍💼 Admin
- Add, edit, and delete products
- Manage categories (with active/inactive state)
- Upload and display product images
- Set product discounts (0–99%)
- Manage stock availability

### 👤 User
- Browse all available products
- Search products by keyword
- Filter products by category
- View product details (title, description, price, discount, stock)
- See discounted prices with strike-through original price
- 🚧 **(Coming Soon)** Add to Cart, Checkout, Authentication

---

## 🛠️ Tech Stack

- **Backend:** Java 21, Spring Boot 3.5, Spring Data JPA, Hibernate
- **Frontend:** Thymeleaf, Bootstrap 5, HTML5, CSS3
- **Database:** MySQL 8
- **Build Tool:** Maven
- **Server:** Embedded Tomcat

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the repository
```bash
git clone https://github.com/mrityunjay0/eCommerce.git
cd ecommerce-springboot
```

### 2️⃣ Configure Database
Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

The app will start at:  
👉 `http://localhost:8080`

---

## 📂 Project Structure

```
com.eCommerce
 ┣ 📂 controller
 ┃ ┣ 📄 AdminController.java
 ┃ ┗ 📄 HomeController.java
 ┣ 📂 entity
 ┃ ┣ 📄 Category.java
 ┃ ┗ 📄 Product.java
 ┣ 📂 repository
 ┃ ┣ 📄 CategoryRepository.java
 ┃ ┗ 📄 ProductRepository.java
 ┣ 📂 service
 ┃ ┣ 📄 CategoryService.java
 ┃ ┣ 📄 CommonService.java
 ┃ ┗ 📄 ProductService.java
 ┣ 📂 serviceImpl
 ┃ ┣ 📄 CategoryServiceImpl.java
 ┃ ┣ 📄 CommonServiceImpl.java
 ┃ ┗ 📄 ProductServiceImpl.java
 ┣ 📄 ECommerceApplication.java
 ┣ 📂 resources
 ┃ ┣ 📂 static
 ┃ ┗ 📂 templates
 ┃   ┣ 📂 admin
 ┃   ┃ ┣ 📄 addCategory.html
 ┃   ┃ ┣ 📄 addProduct.html
 ┃   ┃ ┣ 📄 editCategory.html
 ┃   ┃ ┣ 📄 editProduct.html
 ┃   ┃ ┣ 📄 index.html
 ┃   ┃ ┗ 📄 viewProducts.html
 ┃   ┣ 📄 base.html
 ┃   ┣ 📄 home.html
 ┃   ┣ 📄 login.html
 ┃   ┣ 📄 products.html
 ┃   ┣ 📄 register.html
 ┃   ┗ 📄 viewProduct.html
```

---

## 🚧 Current Status

This project is **in progress**. Upcoming features include:
- 🔐 User Authentication & Authorization (Spring Security)
- 🛒 Shopping Cart & Checkout
- 📦 Order Management & Tracking

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create your feature branch:
   ```bash
   git checkout -b feature/YourFeature
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add new feature"
   ```
4. Push to the branch:
   ```bash
   git push origin feature/YourFeature
   ```
5. Open a Pull Request

---

## 👨‍💻 Author

**Mrityunjay Kumar**
- 🌐 [GitHub](https://github.com/your-username)
- 💼 [LinkedIn](https://www.linkedin.com/in/your-profile)  