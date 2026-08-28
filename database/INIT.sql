/* CREATING THE DATABASE */
CREATE DATABASE bus_fleet_management_001 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

/* CREATING THE USER FOR THE APP */
CREATE USER 'bus_fleet_management_001'@'localhost' IDENTIFIED BY '4vQ56#&hA5tS';

/* SETTING PERMISSIONS FOR THE USER */
GRANT ALL PRIVILEGES ON bus_fleet_management_001.* TO 'bus_fleet_management_001'@'localhost';
FLUSH PRIVILEGES;