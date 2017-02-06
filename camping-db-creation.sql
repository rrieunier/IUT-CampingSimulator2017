-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema CampingSimulator
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `CampingSimulator` ;

-- -----------------------------------------------------
-- Schema CampingSimulator
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CampingSimulator` DEFAULT CHARACTER SET utf8 ;
USE `CampingSimulator` ;

-- -----------------------------------------------------
-- Table `CampingSimulator`.`Log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Log` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Log` (
  `idLog` INT NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL DEFAULT NOW(),
  `action` VARCHAR(150) NOT NULL,
  `user_Host` VARCHAR(60) NOT NULL,
  `user_User` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`idLog`),
  INDEX `fk_Log_user1_idx` (`user_Host` ASC, `user_User` ASC),
  CONSTRAINT `fk_Log_user1`
    FOREIGN KEY (`user_Host` , `user_User`)
    REFERENCES `mysql`.`user` (`Host` , `User`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Employee` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Employee` (
  `idEmployee` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(11) NULL,
  `email` VARCHAR(45) NULL,
  `user_Host` VARCHAR(60) NULL,
  `user_User` VARCHAR(80) NULL,
  `complete_address` VARCHAR(150) NULL,
  PRIMARY KEY (`idEmployee`),
  INDEX `fk_Employee_user1_idx` (`user_Host` ASC, `user_User` ASC),
  CONSTRAINT `fk_Employee_user1`
    FOREIGN KEY (`user_Host` , `user_User`)
    REFERENCES `mysql`.`user` (`Host` , `User`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Location` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Location` (
  `idLocation` INT NOT NULL AUTO_INCREMENT,
  `map_polygon` GEOMETRY NOT NULL,
  PRIMARY KEY (`idLocation`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Task` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Task` (
  `idTask` INT NOT NULL AUTO_INCREMENT,
  `starttime` DATETIME NULL,
  `endtime` DATETIME NULL,
  `label` VARCHAR(50) NOT NULL,
  `idEmployee` INT NOT NULL,
  `idLocation` INT NULL,
  PRIMARY KEY (`idTask`),
  INDEX `fk_Task_Employee1_idx` (`idEmployee` ASC),
  INDEX `fk_Task_Location1_idx` (`idLocation` ASC),
  CONSTRAINT `fk_Task_Employee1`
    FOREIGN KEY (`idEmployee`)
    REFERENCES `CampingSimulator`.`Employee` (`idEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Location1`
    FOREIGN KEY (`idLocation`)
    REFERENCES `CampingSimulator`.`Location` (`idLocation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Client` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Client` (
  `idClient` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(11) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`idClient`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Spot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Spot` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Spot` (
  `idSpot` INT NOT NULL,
  `price_per_day` FLOAT NULL,
  INDEX `fk_Spot_Location1_idx` (`idSpot` ASC),
  PRIMARY KEY (`idSpot`),
  CONSTRAINT `fk_Spot_Location1`
    FOREIGN KEY (`idSpot`)
    REFERENCES `CampingSimulator`.`Location` (`idLocation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Problem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Problem` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Problem` (
  `idProblem` INT NOT NULL AUTO_INCREMENT,
  `appearance_datetime` DATETIME NOT NULL DEFAULT NOW(),
  `label` VARCHAR(45) NOT NULL,
  `solution_datetime` DATETIME NULL,
  `state` VARCHAR(45) NOT NULL DEFAULT 'non résolu',
  PRIMARY KEY (`idProblem`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Reservation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Reservation` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Reservation` (
  `idClient` INT NOT NULL,
  `idSpot` INT NOT NULL,
  `idReservation` INT NOT NULL AUTO_INCREMENT,
  `client_comment` VARCHAR(250) NULL,
  PRIMARY KEY (`idReservation`),
  INDEX `fk_Client_has_Spot_Spot1_idx` (`idSpot` ASC),
  INDEX `fk_Client_has_Spot_Client1_idx` (`idClient` ASC),
  CONSTRAINT `fk_Client_has_Spot_Client1`
    FOREIGN KEY (`idClient`)
    REFERENCES `CampingSimulator`.`Client` (`idClient`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Client_has_Spot_Spot1`
    FOREIGN KEY (`idSpot`)
    REFERENCES `CampingSimulator`.`Spot` (`idSpot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`ClientInvoice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`ClientInvoice` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`ClientInvoice` (
  `idClientInvoice` INT NOT NULL AUTO_INCREMENT,
  `idReservation` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`idClientInvoice`),
  INDEX `fk_ClientInvoice_Reservation1_idx` (`idReservation` ASC),
  CONSTRAINT `fk_ClientInvoice_Reservation1`
    FOREIGN KEY (`idReservation`)
    REFERENCES `CampingSimulator`.`Reservation` (`idReservation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Product` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Product` (
  `idProduct` INT NOT NULL AUTO_INCREMENT,
  `stock` INT NOT NULL DEFAULT 0,
  `sell_price` FLOAT NOT NULL,
  PRIMARY KEY (`idProduct`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Supplier`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Supplier` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Supplier` (
  `idSupplier` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(11) NULL,
  `email` VARCHAR(45) NULL,
  `website` VARCHAR(45) NULL,
  PRIMARY KEY (`idSupplier`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Client_has_Problem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Client_has_Problem` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Client_has_Problem` (
  `idClient` INT NOT NULL,
  `idProblem` INT NOT NULL,
  `discount` FLOAT NOT NULL DEFAULT 0,
  INDEX `fk_Client_has_Problem_Problem1_idx` (`idProblem` ASC),
  INDEX `fk_Client_has_Problem_Client1_idx` (`idClient` ASC),
  PRIMARY KEY (`idClient`, `idProblem`),
  CONSTRAINT `fk_Client_has_Problem_Client1`
    FOREIGN KEY (`idClient`)
    REFERENCES `CampingSimulator`.`Client` (`idClient`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Client_has_Problem_Problem1`
    FOREIGN KEY (`idProblem`)
    REFERENCES `CampingSimulator`.`Problem` (`idProblem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Location_has_Problem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Location_has_Problem` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Location_has_Problem` (
  `idLocation` INT NOT NULL,
  `idProblem` INT NOT NULL,
  INDEX `fk_Location_has_Problem_Problem1_idx` (`idProblem` ASC),
  INDEX `fk_Location_has_Problem_Location1_idx` (`idLocation` ASC),
  PRIMARY KEY (`idLocation`, `idProblem`),
  CONSTRAINT `fk_Location_has_Problem_Location1`
    FOREIGN KEY (`idLocation`)
    REFERENCES `CampingSimulator`.`Location` (`idLocation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Location_has_Problem_Problem1`
    FOREIGN KEY (`idProblem`)
    REFERENCES `CampingSimulator`.`Problem` (`idProblem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Purchase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Purchase` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Purchase` (
  `idPurchase` INT NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL DEFAULT NOW(),
  `quantity` INT NOT NULL,
  `idProduct` INT NOT NULL,
  `Client_idClient` INT NOT NULL,
  PRIMARY KEY (`idPurchase`),
  INDEX `fk_Purchase_Product1_idx` (`idProduct` ASC),
  INDEX `fk_Purchase_Client1_idx` (`Client_idClient` ASC),
  CONSTRAINT `fk_Purchase_Product1`
    FOREIGN KEY (`idProduct`)
    REFERENCES `CampingSimulator`.`Product` (`idProduct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Purchase_Client1`
    FOREIGN KEY (`Client_idClient`)
    REFERENCES `CampingSimulator`.`Client` (`idClient`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Supplier_has_Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Supplier_has_Product` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Supplier_has_Product` (
  `idSupplier` INT NOT NULL,
  `idProduct` INT NOT NULL,
  `buy_price` FLOAT NOT NULL,
  PRIMARY KEY (`idSupplier`, `idProduct`),
  INDEX `fk_Supplier_has_Product_Product1_idx` (`idProduct` ASC),
  INDEX `fk_Supplier_has_Product_Supplier1_idx` (`idSupplier` ASC),
  CONSTRAINT `fk_Supplier_has_Product_Supplier1`
    FOREIGN KEY (`idSupplier`)
    REFERENCES `CampingSimulator`.`Supplier` (`idSupplier`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Supplier_has_Product_Product1`
    FOREIGN KEY (`idProduct`)
    REFERENCES `CampingSimulator`.`Product` (`idProduct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Restocking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Restocking` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Restocking` (
  `idRestocking` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NULL,
  `datetime` DATETIME NULL,
  `idSupplier` INT NOT NULL,
  `idProduct` INT NOT NULL,
  PRIMARY KEY (`idRestocking`),
  INDEX `fk_Restocking_Supplier1_idx` (`idSupplier` ASC),
  INDEX `fk_Restocking_Product1_idx` (`idProduct` ASC),
  CONSTRAINT `fk_Restocking_Supplier1`
    FOREIGN KEY (`idSupplier`)
    REFERENCES `CampingSimulator`.`Supplier` (`idSupplier`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Restocking_Product1`
    FOREIGN KEY (`idProduct`)
    REFERENCES `CampingSimulator`.`Product` (`idProduct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
