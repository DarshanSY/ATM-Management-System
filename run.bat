@echo off
echo Starting ATM Management System...
cd ATM-Management-System-main
java -cp "bin;C:\Users\darsh\.m2\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar" com.atm.gui.LoginFrame
pause
