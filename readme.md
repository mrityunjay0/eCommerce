
# 🛍️ E-Commerce Web Application (Under Development)

A full-stack **E-Commerce application** built using **Spring Boot, Spring Data JPA, Thymeleaf, Bootstrap, and MySQL**.  
This project is **currently under development** 🚧. New features like cart and checkout are in progress.

---

## **Features**

### 👥 User Features
- User registration and login
- Browse products by category
- View product details
- Secure authentication with Spring Security
- **Forgot Password & Reset Password** using secure email link

### 🛠️ Admin Features
- Add, update, delete products
- Manage categories
- Enable/disable user accounts
- View all registered users
- Admin dashboard for product and user management

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

---

### 2. Configure the Database
- Create a MySQL database, for example: `eCommerce_db`.
- Update the `application.properties` file with your database credentials:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  spring.jpa.hibernate.ddl-auto=update
  ```

---

### 3. Configure Mail Service (For Forgot Password)
To enable the Forgot Password feature, configure **Gmail SMTP**:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password  # Use Gmail App Password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

> ⚠️ **Note:**
> - You must enable 2-Step Verification on your Gmail account and generate an **App Password**.
> - The email configured above will be used to send password reset links.

---

### 4. Build the Project
```bash
mvn clean install
```

---

### 5. Run the Application
```bash
mvn spring-boot:run
```

---

### 6. Access the Application
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
 ┃ ┣ 📄 SecurityConfig.java           # Spring Security configuration
 ┃ ┣ 📄 CustomUser.java                # Custom UserDetails implementation
 ┃ ┣ 📄 UserDetailsServiceImpl.java    # Loads user-specific data for authentication
 ┃ ┣ 📄 AuthFailureHandlerImpl.java    # Custom authentication failure handler
 ┃ ┗ 📄 AuthSuccessHandlerImpl.java    # Custom authentication success handler
 ┃
 ┣ 📂 controller
 ┃ ┣ 📄 AdminController.java           # Admin actions and routes
 ┃ ┣ 📄 UserController.java            # User actions and routes
 ┃ ┗ 📄 HomeController.java            # Public & user-facing pages, Forgot/Reset Password
 ┃
 ┣ 📂 entity
 ┃ ┣ 📄 Category.java                  # Product categories
 ┃ ┣ 📄 Product.java                   # Products
 ┃ ┗ 📄 User.java                      # Users
 ┃
 ┣ 📂 repository
 ┃ ┣ 📄 CategoryRepository.java        # JPA repository for Category
 ┃ ┣ 📄 ProductRepository.java         # JPA repository for Product
 ┃ ┗ 📄 UserRepository.java            # JPA repository for User
 ┃
 ┣ 📂 service
 ┃ ┣ 📄 CategoryService.java           # Category business logic
 ┃ ┣ 📄 ProductService.java            # Product business logic
 ┃ ┣ 📄 UserService.java               # User management logic
 ┃ ┗ 📄 CommonService.java             # Generic helper service
 ┃
 ┣ 📂 serviceImpl
 ┃ ┣ 📄 CategoryServiceImpl.java       # Implementation of CategoryService
 ┃ ┣ 📄 ProductServiceImpl.java        # Implementation of ProductService
 ┃ ┣ 📄 UserServiceImpl.java           # Implementation of UserService
 ┃ ┗ 📄 CommonServiceImpl.java         # Implementation of CommonService
 ┃
 ┣ 📂 utils
 ┃ ┣ 📄 AppConstant.java               # App contant variables
 ┃ ┗ 📄 CommonUtils.java               # Utility class for email sending, URL generation
 ┃
 ┣ 📄 ECommerceApplication.java        # Spring Boot starter class
 ┃
 ┣ 📂 resources
 ┃ ┣ 📂 static                          # Static files
 ┃ │   ┣ 📂 css
 ┃ │   ┣ 📂 js
 ┃ │   ┗ 📂 img
 ┃ │       ├── product_img/
 ┃ │       ├── profile_img/
 ┃ │       └── category_img/
 ┃ │
 ┃ ┗ 📂 templates
 ┃     ┣ 📂 admin                       # Admin pages
 ┃     │   ┣ 📄 addCategory.html
 ┃     │   ┣ 📄 addProduct.html
 ┃     │   ┣ 📄 editCategory.html
 ┃     │   ┣ 📄 editProduct.html
 ┃     │   ┣ 📄 index.html
 ┃     │   ┣ 📄 users.html
 ┃     │   ┗ 📄 viewProducts.html
 ┃     │
 ┃     ┣ 📄 base.html                   # Common layout
 ┃     ┣ 📄 errorMessage.html           # Shows error messages while resetting password
 ┃     ┣ 📄 home.html                   # Homepage
 ┃     ┣ 📄 login.html                  # Login page
 ┃     ┣ 📄 register.html               # Registration page
 ┃     ┣ 📄 products.html               # Product listing
 ┃     ┣ 📄 forgotPassword.html         # Forgot password page
 ┃     ┣ 📄 resetPassword.html          # Reset password page (with token)
 ┃     ┗ 📄 viewProduct.html            # Product details
```

---

## **New Forgot Password Flow**
The following is how the reset password process works:
1. **User clicks "Forgot Password"** on the login page.
2. Enters their registered email.
3. Application generates a **secure token** and stores it in the database.
4. User receives an **email with a reset link**.
5. User clicks the link → Reset Password form loads with token.
6. User enters new password and confirms it.
7. On success, token is cleared and the password is updated.

---

## 🚧 Current Status

The project is **under development**.  
Upcoming features include:
- Shopping cart and checkout
- Payment gateway integration
- Product reviews and ratings
- Order tracking system

---

## 📧 Contact
**Author:** Mrityunjay Kumar
- 🌐 [GitHub](https://github.com/mrityunjay0)
- 💼 [LinkedIn](https://www.linkedin.com/in/mrityunjay555/)  
