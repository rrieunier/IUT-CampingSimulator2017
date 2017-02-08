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
-- Table `CampingSimulator`.`Employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Employee` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Employee` (
  `idEmployee` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(11) NULL,
  `email` VARCHAR(45) NULL,
  `complete_address` VARCHAR(150) NULL,
  PRIMARY KEY (`idEmployee`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`User` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`User` (
  `login` VARCHAR(20) NOT NULL,
  `password` MEDIUMTEXT NOT NULL,
  `Employee_idEmployee` INT NULL,
  PRIMARY KEY (`login`),
  UNIQUE INDEX `Usercol_UNIQUE` (`login` ASC),
  INDEX `fk_User_Employee1_idx` (`Employee_idEmployee` ASC),
  CONSTRAINT `fk_User_Employee1`
    FOREIGN KEY (`Employee_idEmployee`)
    REFERENCES `CampingSimulator`.`Employee` (`idEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Log` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Log` (
  `idLog` INT NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL DEFAULT NOW(),
  `action` VARCHAR(150) NOT NULL,
  `user` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idLog`),
  INDEX `fk_Log_User1_idx` (`user` ASC),
  CONSTRAINT `fk_Log_User1`
    FOREIGN KEY (`user`)
    REFERENCES `CampingSimulator`.`User` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Map` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Map` (
  `idMap` INT NOT NULL AUTO_INCREMENT,
  `image` MEDIUMBLOB NOT NULL,
  PRIMARY KEY (`idMap`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Location` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Location` (
  `idLocation` INT NOT NULL AUTO_INCREMENT,
  `point` POINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `idMap` INT NOT NULL,
  PRIMARY KEY (`idLocation`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_Location_Map1_idx` (`idMap` ASC),
  CONSTRAINT `fk_Location_Map1`
    FOREIGN KEY (`idMap`)
    REFERENCES `CampingSimulator`.`Map` (`idMap`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Task` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Task` (
  `idTask` INT NOT NULL AUTO_INCREMENT,
  `starttime` DATETIME NOT NULL,
  `endtime` DATETIME NOT NULL,
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
-- Table `CampingSimulator`.`SpotType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`SpotType` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`SpotType` (
  `idSpotType` INT NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSpotType`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Spot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Spot` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Spot` (
  `idSpot` INT NOT NULL,
  `price_per_day` FLOAT NOT NULL,
  `capacity` INT NOT NULL DEFAULT 1,
  `water` TINYINT(1) NOT NULL,
  `electricity` TINYINT(1) NOT NULL,
  `shadow` TINYINT(1) NOT NULL,
  `table1_SpotType` INT NOT NULL,
  INDEX `fk_Spot_Location1_idx` (`idSpot` ASC),
  PRIMARY KEY (`idSpot`),
  INDEX `fk_Spot_table11_idx` (`table1_SpotType` ASC),
  CONSTRAINT `fk_Spot_Location1`
    FOREIGN KEY (`idSpot`)
    REFERENCES `CampingSimulator`.`Location` (`idLocation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Spot_table11`
    FOREIGN KEY (`table1_SpotType`)
    REFERENCES `CampingSimulator`.`SpotType` (`idSpotType`)
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
-- Table `CampingSimulator`.`Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Product` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Product` (
  `idProduct` INT NOT NULL AUTO_INCREMENT,
  `stock` INT NOT NULL DEFAULT 0,
  `sell_price` FLOAT NOT NULL,
  `label` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idProduct`),
  UNIQUE INDEX `label_UNIQUE` (`label` ASC))
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
-- Table `CampingSimulator`.`Reservation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Reservation` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Reservation` (
  `idClient` INT NOT NULL,
  `idSpot` INT NOT NULL,
  `idReservation` INT NOT NULL AUTO_INCREMENT,
  `client_comment` VARCHAR(250) NULL,
  `startdate` DATETIME NOT NULL,
  `enddate` DATETIME NOT NULL,
  `reservation_date` DATETIME NOT NULL DEFAULT NOW(),
  `person_count` INT NOT NULL,
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
-- Table `CampingSimulator`.`Purchase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Purchase` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Purchase` (
  `idPurchase` INT NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL DEFAULT NOW(),
  `quantity` INT NOT NULL,
  `idProduct` INT NOT NULL,
  `idClient` INT NOT NULL,
  PRIMARY KEY (`idPurchase`),
  INDEX `fk_Purchase_Product1_idx` (`idProduct` ASC),
  INDEX `fk_Purchase_Client1_idx` (`idClient` ASC),
  CONSTRAINT `fk_Purchase_Product1`
    FOREIGN KEY (`idProduct`)
    REFERENCES `CampingSimulator`.`Product` (`idProduct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Purchase_Client1`
    FOREIGN KEY (`idClient`)
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


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Authorization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Authorization` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Authorization` (
  `idAuthorization` INT NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(45) NULL,
  PRIMARY KEY (`idAuthorization`),
  UNIQUE INDEX `label_UNIQUE` (`label` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`User_has_Authorization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`User_has_Authorization` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`User_has_Authorization` (
  `login` VARCHAR(20) NOT NULL,
  `idAuthorization` INT NOT NULL,
  INDEX `fk_User_has_Authorization_Authorization1_idx` (`idAuthorization` ASC),
  INDEX `fk_User_has_Authorization_User1_idx` (`login` ASC),
  PRIMARY KEY (`login`, `idAuthorization`),
  CONSTRAINT `fk_User_has_Authorization_User1`
    FOREIGN KEY (`login`)
    REFERENCES `CampingSimulator`.`User` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Authorization_Authorization1`
    FOREIGN KEY (`idAuthorization`)
    REFERENCES `CampingSimulator`.`Authorization` (`idAuthorization`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `CampingSimulator` ;

-- -----------------------------------------------------
-- Placeholder table for view `CampingSimulator`.`PurchasesOnReservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CampingSimulator`.`PurchasesOnReservation` (`idClient` INT, `idReservation` INT, `idSpot` INT, `idPurchase` INT);

-- -----------------------------------------------------
-- View `CampingSimulator`.`PurchasesOnReservation`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `CampingSimulator`.`PurchasesOnReservation` ;
DROP TABLE IF EXISTS `CampingSimulator`.`PurchasesOnReservation`;
USE `CampingSimulator`;
CREATE  OR REPLACE VIEW `PurchasesOnReservation` AS
SELECT Client.idClient, Reservation.idReservation, idSpot, idPurchase
FROM Reservation
INNER JOIN Client ON Reservation.idClient = Client.idClient
INNER JOIN Purchase ON Client.idClient = Purchase.idClient
WHERE Purchase.datetime >= Reservation.startdate AND Purchase.datetime <= Reservation.enddate;
SET SQL_MODE = '';
GRANT USAGE ON *.* TO camping;
 DROP USER camping;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'camping' IDENTIFIED BY 'camping';

GRANT SELECT, INSERT, TRIGGER ON TABLE `CampingSimulator`.* TO 'camping';
GRANT SELECT ON TABLE `CampingSimulator`.* TO 'camping';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `CampingSimulator`.* TO 'camping';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Employee`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Employee` (`idEmployee`, `last_name`, `first_name`, `phone`, `email`, `complete_address`) VALUES (DEFAULT, 'Deménache', 'Fam', NULL, NULL, NULL);
INSERT INTO `CampingSimulator`.`Employee` (`idEmployee`, `last_name`, `first_name`, `phone`, `email`, `complete_address`) VALUES (DEFAULT, 'Rieunier', 'Roman', NULL, NULL, NULL);
INSERT INTO `CampingSimulator`.`Employee` (`idEmployee`, `last_name`, `first_name`, `phone`, `email`, `complete_address`) VALUES (DEFAULT, 'Dupouy', 'Sylvain', NULL, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`User` (`login`, `password`, `Employee_idEmployee`) VALUES ('campingadmin', 'admin', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Client`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Client` (`idClient`, `last_name`, `first_name`, `phone`, `email`) VALUES (DEFAULT, 'Dupont', 'Herbert', '', NULL);
INSERT INTO `CampingSimulator`.`Client` (`idClient`, `last_name`, `first_name`, `phone`, `email`) VALUES (DEFAULT, 'Félix', 'Patrick', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Product`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Product` (`idProduct`, `stock`, `sell_price`, `label`) VALUES (DEFAULT, 10, 3, 'PAPIER TOILETTE - PACK 12');
INSERT INTO `CampingSimulator`.`Product` (`idProduct`, `stock`, `sell_price`, `label`) VALUES (DEFAULT, 0, 10, 'PATATE - 1KG');

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Supplier`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Supplier` (`idSupplier`, `name`, `phone`, `email`, `website`) VALUES (DEFAULT, 'MegaSupplier2000', '', NULL, NULL);
INSERT INTO `CampingSimulator`.`Supplier` (`idSupplier`, `name`, `phone`, `email`, `website`) VALUES (DEFAULT, 'BailNetwork', '', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Authorization`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Authorization` (`idAuthorization`, `label`) VALUES (DEFAULT, 'RESTOCK_PRODUCTS');
INSERT INTO `CampingSimulator`.`Authorization` (`idAuthorization`, `label`) VALUES (DEFAULT, 'MANAGE_RESERVATIONS');
INSERT INTO `CampingSimulator`.`Authorization` (`idAuthorization`, `label`) VALUES (DEFAULT, 'MANAGE_LOCATIONS');
INSERT INTO `CampingSimulator`.`Authorization` (`idAuthorization`, `label`) VALUES (DEFAULT, 'MANAGE_EMPLOYEES');
INSERT INTO `CampingSimulator`.`Authorization` (`idAuthorization`, `label`) VALUES (DEFAULT, 'MANAGE_USERS');

COMMIT;

