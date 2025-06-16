# 💳 ATM Simulator (Java + MySQL)

This is a console-based ATM Simulator built using:

- ✅ Java with OOP concepts
- ✅ MySQL Database Integration using JDBC
- ✅ Command Line Interface (CLI)

## 📦 Features

- Card authentication
- Balance checking
- Deposit & withdrawal
- Transaction logging (with timestamp)
- Secure pin verification

## ⚙️ Setup Instructions

1. Install MySQL and Java JDK 17+
2. Import the `atmdb` database from `db.sql`
3. Update your DB credentials in `Database.java`
4. Compile:
   ```bash
   javac -cp lib/mysql-connector-j-8.0.33.jar -d bin src/*.java


## to RUN

java -cp "lib/mysql-connector-j-8.0.33.jar;bin" Main

