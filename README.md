# SMS
 a comprehensive school management system


# 💻 how to setup

## 1. Clone the Repository
```bash
git clone https://github.com/uncrepo/SMS.git
```

## 2. Import the Database
### Login to MySQL
```bash
mysql -u root -p
```

### Create the database
CREATE DATABASE school_db;

### Import the SQL file
```bash
mysql -u root -p school_db < database/school_db.sql
```

## 3. Configure DB Connection
```java
String url = "jdbc:mysql://localhost:3306/sms_db";
String user = "root";
String password = "admin";
```

## ✅ 4. **Include All Assets**

Put:
- All your `.fxml` files inside `/resources/fxml/`
- CSS in `/resources/styles/`
- Images in `/resources/images/`

In your Java code, load them like:
```java
FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
