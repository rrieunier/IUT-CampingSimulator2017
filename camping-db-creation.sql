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
  `id` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(11) NULL,
  `email` VARCHAR(45) NULL,
  `complete_address` VARCHAR(150) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`User` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`User` (
  `login` VARCHAR(20) NOT NULL,
  `password` MEDIUMTEXT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `Employee_id` INT NULL,
  UNIQUE INDEX `Usercol_UNIQUE` (`login` ASC),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_User_Employee1_idx` (`Employee_id` ASC),
  CONSTRAINT `fk_User_Employee1`
    FOREIGN KEY (`Employee_id`)
    REFERENCES `CampingSimulator`.`Employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Log` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL DEFAULT NOW(),
  `action` VARCHAR(150) NOT NULL,
  `user` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Log_User1_idx` (`user` ASC),
  CONSTRAINT `fk_Log_User1`
    FOREIGN KEY (`user`)
    REFERENCES `CampingSimulator`.`User` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Location` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `point_x` DOUBLE NULL,
  `point_y` DOUBLE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
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
  `Employee_id` INT NOT NULL,
  `Location_id` INT NULL,
  PRIMARY KEY (`idTask`),
  INDEX `fk_Task_Employee1_idx` (`Employee_id` ASC),
  INDEX `fk_Task_Location1_idx` (`Location_id` ASC),
  CONSTRAINT `fk_Task_Employee1`
    FOREIGN KEY (`Employee_id`)
    REFERENCES `CampingSimulator`.`Employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Location1`
    FOREIGN KEY (`Location_id`)
    REFERENCES `CampingSimulator`.`Location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Client` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(11) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`SpotType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`SpotType` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`SpotType` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Spot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Spot` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Spot` (
  `price_per_day` FLOAT NOT NULL,
  `capacity` INT NOT NULL DEFAULT 1,
  `water` TINYINT(1) NOT NULL,
  `electricity` TINYINT(1) NOT NULL,
  `shadow` TINYINT(1) NOT NULL,
  `id` INT NOT NULL,
  `SpotType_id` INT NOT NULL,
  INDEX `fk_Spot_Location1_idx` (`id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_Spot_SpotType1_idx` (`SpotType_id` ASC),
  CONSTRAINT `fk_Spot_Location1`
    FOREIGN KEY (`id`)
    REFERENCES `CampingSimulator`.`Location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Spot_SpotType1`
    FOREIGN KEY (`SpotType_id`)
    REFERENCES `CampingSimulator`.`SpotType` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Problem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Problem` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Problem` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `appearance_datetime` DATETIME NOT NULL DEFAULT NOW(),
  `label` VARCHAR(45) NOT NULL,
  `solution_datetime` DATETIME NULL,
  `state` VARCHAR(45) NOT NULL DEFAULT 'non résolu',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Product` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `stock` INT NOT NULL DEFAULT 0,
  `sell_price` FLOAT NOT NULL,
  `label` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `label_UNIQUE` (`label` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Supplier`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Supplier` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Supplier` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(11) NULL,
  `email` VARCHAR(45) NULL,
  `website` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Reservation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Reservation` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Reservation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `client_comment` VARCHAR(250) NULL,
  `starttime` DATETIME NOT NULL,
  `endtime` DATETIME NOT NULL,
  `reservation_date` DATETIME NOT NULL DEFAULT NOW(),
  `person_count` INT NOT NULL,
  `Spot_id` INT NOT NULL,
  `Client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Reservation_Spot1_idx` (`Spot_id` ASC),
  INDEX `fk_Reservation_Client1_idx` (`Client_id` ASC),
  CONSTRAINT `fk_Reservation_Spot1`
    FOREIGN KEY (`Spot_id`)
    REFERENCES `CampingSimulator`.`Spot` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reservation_Client1`
    FOREIGN KEY (`Client_id`)
    REFERENCES `CampingSimulator`.`Client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Purchase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Purchase` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Purchase` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL DEFAULT NOW(),
  `quantity` INT NOT NULL,
  `Product_id` INT NOT NULL,
  `Client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Purchase_Product1_idx` (`Product_id` ASC),
  INDEX `fk_Purchase_Client1_idx` (`Client_id` ASC),
  CONSTRAINT `fk_Purchase_Product1`
    FOREIGN KEY (`Product_id`)
    REFERENCES `CampingSimulator`.`Product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Purchase_Client1`
    FOREIGN KEY (`Client_id`)
    REFERENCES `CampingSimulator`.`Client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Supplier_has_Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Supplier_has_Product` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Supplier_has_Product` (
  `Supplier_id` INT NOT NULL,
  `Product_id` INT NOT NULL,
  `buy_price` FLOAT NOT NULL,
  PRIMARY KEY (`Supplier_id`, `Product_id`),
  INDEX `fk_Supplier_has_Product_Product1_idx` (`Product_id` ASC),
  INDEX `fk_Supplier_has_Product_Supplier1_idx` (`Supplier_id` ASC),
  CONSTRAINT `fk_Supplier_has_Product_Supplier1`
    FOREIGN KEY (`Supplier_id`)
    REFERENCES `CampingSimulator`.`Supplier` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Supplier_has_Product_Product1`
    FOREIGN KEY (`Product_id`)
    REFERENCES `CampingSimulator`.`Product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Restocking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Restocking` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Restocking` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `datetime` DATETIME NOT NULL DEFAULT NOW(),
  `Supplier_id` INT NOT NULL,
  `Product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Restocking_Supplier1_idx` (`Supplier_id` ASC),
  INDEX `fk_Restocking_Product1_idx` (`Product_id` ASC),
  CONSTRAINT `fk_Restocking_Supplier1`
    FOREIGN KEY (`Supplier_id`)
    REFERENCES `CampingSimulator`.`Supplier` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Restocking_Product1`
    FOREIGN KEY (`Product_id`)
    REFERENCES `CampingSimulator`.`Product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Authorization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Authorization` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Authorization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `label_UNIQUE` (`label` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Map` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Map` (
  `image` MEDIUMBLOB NOT NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`User_has_Authorization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`User_has_Authorization` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`User_has_Authorization` (
  `Authorization_id` INT NOT NULL,
  `User_id` INT NOT NULL,
  PRIMARY KEY (`Authorization_id`, `User_id`),
  INDEX `fk_Authorization_has_User_User1_idx` (`User_id` ASC),
  INDEX `fk_Authorization_has_User_Authorization1_idx` (`Authorization_id` ASC),
  CONSTRAINT `fk_Authorization_has_User_Authorization1`
    FOREIGN KEY (`Authorization_id`)
    REFERENCES `CampingSimulator`.`Authorization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Authorization_has_User_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `CampingSimulator`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Location_has_Problem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Location_has_Problem` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Location_has_Problem` (
  `Location_id` INT NOT NULL,
  `Problem_id` INT NOT NULL,
  PRIMARY KEY (`Location_id`, `Problem_id`),
  INDEX `fk_Location_has_Problem_Problem1_idx` (`Problem_id` ASC),
  INDEX `fk_Location_has_Problem_Location1_idx` (`Location_id` ASC),
  CONSTRAINT `fk_Location_has_Problem_Location1`
    FOREIGN KEY (`Location_id`)
    REFERENCES `CampingSimulator`.`Location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Location_has_Problem_Problem1`
    FOREIGN KEY (`Problem_id`)
    REFERENCES `CampingSimulator`.`Problem` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Client_has_Problem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Client_has_Problem` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Client_has_Problem` (
  `Client_id` INT NOT NULL,
  `Problem_id` INT NOT NULL,
  PRIMARY KEY (`Client_id`, `Problem_id`),
  INDEX `fk_Client_has_Problem_Problem1_idx` (`Problem_id` ASC),
  INDEX `fk_Client_has_Problem_Client1_idx` (`Client_id` ASC),
  CONSTRAINT `fk_Client_has_Problem_Client1`
    FOREIGN KEY (`Client_id`)
    REFERENCES `CampingSimulator`.`Client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Client_has_Problem_Problem1`
    FOREIGN KEY (`Problem_id`)
    REFERENCES `CampingSimulator`.`Problem` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `CampingSimulator` ;

-- -----------------------------------------------------
-- Placeholder table for view `CampingSimulator`.`PurchasesOnReservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CampingSimulator`.`PurchasesOnReservation` (`Reservation_id` INT, `Purchase_id` INT);

-- -----------------------------------------------------
-- View `CampingSimulator`.`PurchasesOnReservation`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `CampingSimulator`.`PurchasesOnReservation` ;
DROP TABLE IF EXISTS `CampingSimulator`.`PurchasesOnReservation`;
USE `CampingSimulator`;
CREATE  OR REPLACE VIEW `PurchasesOnReservation` AS
SELECT Reservation.id as Reservation_id, Purchase.id as Purchase_id
FROM Reservation
INNER JOIN Client ON Reservation.Client_id = Client.id
INNER JOIN Purchase ON Client.id = Purchase.id
WHERE Purchase.datetime >= Reservation.starttime AND Purchase.datetime <= Reservation.endtime;
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
INSERT INTO `CampingSimulator`.`Employee` (`id`, `last_name`, `first_name`, `phone`, `email`, `complete_address`) VALUES (DEFAULT, 'Deménache', 'Fam', NULL, NULL, NULL);
INSERT INTO `CampingSimulator`.`Employee` (`id`, `last_name`, `first_name`, `phone`, `email`, `complete_address`) VALUES (DEFAULT, 'Rieunier', 'Roman', NULL, NULL, NULL);
INSERT INTO `CampingSimulator`.`Employee` (`id`, `last_name`, `first_name`, `phone`, `email`, `complete_address`) VALUES (DEFAULT, 'Dupouy', 'Sylvain', NULL, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`User` (`login`, `password`, `id`, `Employee_id`) VALUES ('campingadmin', 'admin', DEFAULT, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Client`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Client` (`id`, `last_name`, `first_name`, `phone`, `email`) VALUES (DEFAULT, 'Dupont', 'Herbert', '', NULL);
INSERT INTO `CampingSimulator`.`Client` (`id`, `last_name`, `first_name`, `phone`, `email`) VALUES (DEFAULT, 'Félix', 'Patrick', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`SpotType`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`SpotType` (`id`, `label`) VALUES (DEFAULT, 'TENTE');
INSERT INTO `CampingSimulator`.`SpotType` (`id`, `label`) VALUES (DEFAULT, 'BUNGALO');
INSERT INTO `CampingSimulator`.`SpotType` (`id`, `label`) VALUES (DEFAULT, 'CARAVANE');

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Product`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Product` (`id`, `stock`, `sell_price`, `label`) VALUES (DEFAULT, 10, 3, 'PAPIER TOILETTE - PACK 12');
INSERT INTO `CampingSimulator`.`Product` (`id`, `stock`, `sell_price`, `label`) VALUES (DEFAULT, 0, 10, 'PATATE - 1KG');

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Supplier`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Supplier` (`id`, `name`, `phone`, `email`, `website`) VALUES (DEFAULT, 'MegaSupplier2000', '', NULL, NULL);
INSERT INTO `CampingSimulator`.`Supplier` (`id`, `name`, `phone`, `email`, `website`) VALUES (DEFAULT, 'BailNetwork', '', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CampingSimulator`.`Authorization`
-- -----------------------------------------------------
START TRANSACTION;
USE `CampingSimulator`;
INSERT INTO `CampingSimulator`.`Authorization` (`id`, `label`) VALUES (DEFAULT, 'RESTOCK_PRODUCTS');
INSERT INTO `CampingSimulator`.`Authorization` (`id`, `label`) VALUES (DEFAULT, 'MANAGE_RESERVATIONS');
INSERT INTO `CampingSimulator`.`Authorization` (`id`, `label`) VALUES (DEFAULT, 'MANAGE_LOCATIONS');
INSERT INTO `CampingSimulator`.`Authorization` (`id`, `label`) VALUES (DEFAULT, 'MANAGE_EMPLOYEES');
INSERT INTO `CampingSimulator`.`Authorization` (`id`, `label`) VALUES (DEFAULT, 'MANAGE_USERS');

COMMIT;

USE `CampingSimulator`;

DELIMITER $$

USE `CampingSimulator`$$
DROP TRIGGER IF EXISTS `CampingSimulator`.`Task_BEFORE_INSERT` $$
USE `CampingSimulator`$$
CREATE DEFINER = CURRENT_USER TRIGGER `CampingSimulator`.`Task_BEFORE_INSERT` BEFORE INSERT ON `Task` 
FOR EACH ROW
BEGIN
	DECLARE count INT;
    
    IF NEW.starttime < NEW.endtime THEN
    		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERREUR : La date de fin est avant la date de début';
    END IF;
    
	SELECT COUNT(*) INTO count from Task 
		WHERE Employee_id = NEW.Employee_id
        AND starttime <= NEW.starttime
		AND endtime >= NEW.endtime;
	
    IF count > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERREUR : Une tâche est déjà assignée pour ce créneau';
    END IF;
END;$$


USE `CampingSimulator`$$
DROP TRIGGER IF EXISTS `CampingSimulator`.`Reservation_BEFORE_INSERT` $$
USE `CampingSimulator`$$
CREATE DEFINER = CURRENT_USER TRIGGER `CampingSimulator`.`Reservation_BEFORE_INSERT` BEFORE INSERT ON `Reservation` FOR EACH ROW
BEGIN
DECLARE count INT;

	IF NEW.starttime < NEW.endtime THEN
    		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERREUR : La date de fin est avant la date de début';
    END IF;

	SELECT COUNT(*) INTO count from Reservation 
		WHERE Client_id = NEW.Client_id
        AND starttime <= NEW.starttime
		AND endtime >= NEW.endtime;
	
    IF count > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERREUR : Ce client a déjà effectué une réservation durant cette période';
    END IF;
    
    SELECT COUNT(*) INTO count from Reservation 
		WHERE Spot_id = NEW.Spot_id
        AND starttime <= NEW.starttime
		AND endtime >= NEW.endtime;
	
    IF count > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ERREUR : Cette emplacement est déjà réservé sur cette période';
    END IF;
END$$


DELIMITER ;
