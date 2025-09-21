# 🛍️ E-Commerce Web Application (Under Development)

A full-stack **E-Commerce application** built using **Spring Boot, Spring Data JPA, Thymeleaf, Bootstrap, and MySQL**.  
This project is **currently under development** 🚧. New features like cart, checkout are being implemented.

---

## **Features**
### 👥 User Features
- User registration and login
- Browse products by category
- View product details
- Secure authentication with Spring Security

### 🛠️ Admin Features
- Add, update, delete products
- Manage categories
- Enable/disable user accounts
- View all registered users
- Admin dashboard with product management

---

## **Tech Stack**
- **Backend:** Spring Boot, Spring MVC, Spring Security, Spring Data JPA (Hibernate)
- **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap
- **Database:** MySQL
- **Build Tool:** Maven
- **Java Version:** 17+ (or the version specified in `pom.xml`)

---

## **Getting Started**

### 1. Clone the Repository
```bash
git clone https://github.com/mrityunjay0/eCommerce.git
cd eCommerce
```

### 2. Configure the Database
- Create a MySQL database, for example: `eCommerce_db`.
- Update the `application.properties` file with your database credentials:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  spring.jpa.hibernate.ddl-auto=update
  ```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

### 5. Access the Application
- **Frontend:** `http://localhost:8080`
- **Admin Dashboard:** `http://localhost:8080/admin/`

Default admin credentials (if you seeded them manually):
```
Email: Admin Email
Password: Admin Password
```

---

## 📂 Project Structure

```
com.eCommerce
 ┣ 📂 config
 ┃ ┣ 📄 SecurityConfig.java           # Main Spring Security configuration
 ┃ ┣ 📄 CustomUser.java                # Custom UserDetails implementation
 ┃ ┣ 📄 UserDetailsServiceImpl.java    # Loads user-specific data for authentication
 ┃ ┗ 📄 AuthSuccessHandlerImpl.java    # Custom authentication success handler
 ┃
 ┣ 📂 controller
 ┃ ┣ 📄 AdminController.java           # Handles admin-specific routes and actions
 ┃ ┗ 📄 HomeController.java            # Handles public and user-facing pages
 ┃
 ┣ 📂 entity
 ┃ ┣ 📄 Category.java                  # Entity representing product categories
 ┃ ┣ 📄 Product.java                   # Entity representing products
 ┃ ┗ 📄 User.java                      # Entity representing application users
 ┃
 ┣ 📂 repository
 ┃ ┣ 📄 CategoryRepository.java        # JPA repository for Category entity
 ┃ ┣ 📄 ProductRepository.java         # JPA repository for Product entity
 ┃ ┗ 📄 UserRepository.java            # JPA repository for User entity
 ┃
 ┣ 📂 service
 ┃ ┣ 📄 CategoryService.java           # Service interface for Category operations
 ┃ ┣ 📄 ProductService.java            # Service interface for Product operations
 ┃ ┣ 📄 UserService.java               # Service interface for User management
 ┃ ┗ 📄 CommonService.java             # Generic service (e.g., session handling)
 ┃
 ┣ 📂 serviceImpl
 ┃ ┣ 📄 CategoryServiceImpl.java       # Implementation of CategoryService
 ┃ ┣ 📄 ProductServiceImpl.java        # Implementation of ProductService
 ┃ ┣ 📄 UserServiceImpl.java           # Implementation of UserService
 ┃ ┗ 📄 CommonServiceImpl.java         # Implementation of CommonService
 ┃
 ┣ 📄 ECommerceApplication.java        # Main Spring Boot application starter class
 ┃
 ┣ 📂 resources
 ┃ ┣ 📂 static                          # Static frontend resources
 ┃ │   ┣ 📂 css                         # Stylesheets
 ┃ │   ┣ 📂 js                          # JavaScript files
 ┃ │   ┗ 📂 img                         # Images (product, category, profile)
 ┃ │       ├── product_img/
 ┃ │       ├── profile_img/
 ┃ │       └── category_img/
 ┃ │
 ┃ ┗ 📂 templates                       # Thymeleaf templates for UI
 ┃     ┣ 📂 admin
 ┃     │   ┣ 📄 addCategory.html        # Form to add a new category
 ┃     │   ┣ 📄 addProduct.html         # Form to add a new product
 ┃     │   ┣ 📄 editCategory.html       # Form to edit an existing category
 ┃     │   ┣ 📄 editProduct.html        # Form to edit an existing product
 ┃     │   ┣ 📄 index.html              # Admin dashboard
 ┃     │   ┗ 📄 viewProducts.html       # Page to list all products for admin
 ┃     │
 ┃     ┣ 📄 base.html                   # Common layout template
 ┃     ┣ 📄 home.html                   # Homepage for users
 ┃     ┣ 📄 login.html                  # Login page
 ┃     ┣ 📄 products.html               # Product listing page for customers
 ┃     ┣ 📄 register.html               # User registration page
 ┃     ┗ 📄 viewProduct.html            # Product details page for customers
```

---

## 🚧 Current Status

This project is **in progress**. Upcoming features include:
- Shopping cart and checkout
- Payment gateway integration
- Product reviews and ratings
- Order tracking system
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
- 🌐 [GitHub](https://github.com/mrityunjay0)
- 💼 [LinkedIn](https://www.linkedin.com/in/mrityunjay555/)  