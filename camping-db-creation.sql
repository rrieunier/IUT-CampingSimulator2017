DROP DATABASE IF EXISTS Camping;
CREATE DATABASE Camping;
DROP USER IF EXISTS camping;
CREATE USER camping
  IDENTIFIED BY 'camping';

GRANT CREATE, SELECT, DELETE, UPDATE, INSERT, ALTER ON Camping.* TO camping;
