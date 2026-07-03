# ATM Machine Project 🏧

A Java Swing application simulating an Automated Teller Machine (ATM) with a MySQL database backend.

## 🚀 Features

### Core Functionality
*   **Login**: Secure authentication with Card Number and PIN.
*   **Check Balance**: View real-time account balance.
*   **Withdraw & Deposit**: Cash transaction simulation with database updates.
*   **Transaction History**: View past transactions in a table.
*   **Change PIN**: Update your security PIN securely.
*   **Auto Logout**: Automatically logs out after 2 minutes of inactivity.

### 🎨 UI/UX Experience (New!)
*   **Modern Theme**: Deep Blue gradient background with glass-card effects.
*   **Icons**: Intuitive Unicode icons on menu buttons (💸, 💰, 🏦).
*   **Nimbus Look**: Clean, modern OS integration.
*   **Enhanced UX**:
    *   **Numeric Validation**: Inputs automatically reject non-numeric characters.
    *   **Keyboard Support**: Press `Enter` to submit forms.

## 🛠️ Tech Stack
*   **Language**: Java (Swing/AWT)
*   **Database**: MySQL
*   **Driver**: MySQL Connector/J

## ⚙️ Setup & Installation

1.  **Prerequisites**:
    *   Java Development Kit (JDK) 8 or higher.
    *   MySQL Server installed and running.
    *   `mysql-connector-j` library (configured in classpath).

2.  **Database Setup**:
    Create a database named `atmdb` and run the necessary SQL scripts to create `users` and `transactions` tables.

3.  **Compilation**:
    ```bash
    javac -encoding UTF-8 -d bin -cp "path/to/mysql-connector-j.jar" -sourcepath src src/com/atm/gui/LoginFrame.java
    ```

4.  **Running**:
    ```bash
    java -cp "bin;path/to/mysql-connector-j.jar" com.atm.gui.LoginFrame
    ```

## 🔐 Default Credentials
*(Replace with your actual database entries)*
*   **Card Number**: `1234567890123456`
*   **PIN**: `2025`
