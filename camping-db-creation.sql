SET @@global.time_zone = '+01:00';
DROP DATABASE IF EXISTS Camping;
CREATE DATABASE Camping;
DROP USER IF EXISTS camping;
CREATE USER camping
  IDENTIFIED BY 'camping';

GRANT ALL PRIVILEGES ON Camping.* TO camping;
