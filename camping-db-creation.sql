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
  `User_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Log_User1_idx` (`User_id` ASC),
  CONSTRAINT `fk_Log_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `CampingSimulator`.`User` (`id`)
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
  `critical_quantity` INT NOT NULL DEFAULT 0,
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
  `label` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `label_UNIQUE` (`label` ASC),
  PRIMARY KEY (`label`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Map` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Map` (
  `image` MEDIUMBLOB NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`User_has_Authorization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`User_has_Authorization` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`User_has_Authorization` (
  `User_id` INT NOT NULL,
  `Authorization_label` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`User_id`, `Authorization_label`),
  INDEX `fk_Authorization_has_User_User1_idx` (`User_id` ASC),
  INDEX `fk_User_has_Authorization_Authorization1_idx` (`Authorization_label` ASC),
  CONSTRAINT `fk_Authorization_has_User_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `CampingSimulator`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Authorization_Authorization1`
    FOREIGN KEY (`Authorization_label`)
    REFERENCES `CampingSimulator`.`Authorization` (`label`)
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


-- -----------------------------------------------------
-- Table `CampingSimulator`.`Notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`Notification` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`Notification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idNotification_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CampingSimulator`.`User_has_Notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CampingSimulator`.`User_has_Notification` ;

CREATE TABLE IF NOT EXISTS `CampingSimulator`.`User_has_Notification` (
  `User_id` INT NOT NULL,
  `Notification_id` INT NOT NULL,
  PRIMARY KEY (`User_id`, `Notification_id`),
  INDEX `fk_User_has_Notification_Notification1_idx` (`Notification_id` ASC),
  INDEX `fk_User_has_Notification_User1_idx` (`User_id` ASC),
  CONSTRAINT `fk_User_has_Notification_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `CampingSimulator`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Notification_Notification1`
    FOREIGN KEY (`Notification_id`)
    REFERENCES `CampingSimulator`.`Notification` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `CampingSimulator` ;

-- -----------------------------------------------------
-- Placeholder table for view `CampingSimulator`.`PurchasesOnReservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CampingSimulator`.`PurchasesOnReservation` (`Reservation_id` INT, `Purchase_id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `CampingSimulator`.`ReservedOrOccupiedSpots`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CampingSimulator`.`ReservedOrOccupiedSpots` (`id` INT);

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

-- -----------------------------------------------------
-- View `CampingSimulator`.`ReservedOrOccupiedSpots`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `CampingSimulator`.`ReservedOrOccupiedSpots` ;
DROP TABLE IF EXISTS `CampingSimulator`.`ReservedOrOccupiedSpots`;
USE `CampingSimulator`;
CREATE  OR REPLACE VIEW `ReservedOrOccupiedSpots` AS
SELECT Spot.id FROM Spot
INNER JOIN Reservation ON Spot.id = Reservation.Spot_id
WHERE Reservation.endtime >= NOW();
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
DROP TRIGGER IF EXISTS `CampingSimulator`.`Problem_AFTER_INSERT` $$
USE `CampingSimulator`$$
CREATE DEFINER = CURRENT_USER TRIGGER `CampingSimulator`.`Problem_AFTER_INSERT` AFTER INSERT ON `Problem` FOR EACH ROW
BEGIN
DECLARE userid INT;
    DECLARE notifid INT;
	DECLARE msg VARCHAR(200);
    DECLARE done INT DEFAULT FALSE;
	DECLARE cur CURSOR FOR SELECT User_id FROM User_Has_Authorization WHERE Authorization_label = 'PROBLEM_MANAGEMENT';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
			
	SET msg = NEW.label;
	INSERT INTO Notification(title,content) VALUES ('NOUVEAU PROBLEME', msg);
		
    SET notifid = LAST_INSERT_ID(); 
		
    OPEN cur;
    read_loop: LOOP
        
		FETCH cur into userid;
		IF done THEN
			LEAVE read_loop;
		END IF;
			
		INSERT INTO User_has_Notification(User_id, Notification_id) VALUES (userid, notifid);
            
	END LOOP;
END$$


USE `CampingSimulator`$$
DROP TRIGGER IF EXISTS `CampingSimulator`.`Product_AFTER_UPDATE` $$
USE `CampingSimulator`$$
CREATE DEFINER = CURRENT_USER TRIGGER `CampingSimulator`.`Product_AFTER_UPDATE` AFTER UPDATE ON `Product` FOR EACH ROW
BEGIN
	DECLARE userid INT;
    DECLARE notifid INT;
	DECLARE msg VARCHAR(200);
    DECLARE done INT DEFAULT FALSE;
	DECLARE cur CURSOR FOR SELECT User_id FROM User_Has_Authorization WHERE Authorization_label = 'PRODUCT_MANAGEMENT';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	
    IF NEW.stock < NEW.critical_quantity THEN
		
		SET msg = CONCAT('Le produit "', NEW.label, '" a atteint la quantité critique');
		INSERT INTO Notification(title,content) VALUES ('PRODUIT CRITIQUE', msg);
		
        SET notifid = LAST_INSERT_ID(); 
		
        OPEN cur;
        read_loop: LOOP
        
			FETCH cur into userid;
            IF done THEN
				LEAVE read_loop;
			END IF;
			
            INSERT INTO User_has_Notification(User_id, Notification_id) VALUES (userid, notifid);
            
        END LOOP;
    END IF;
END$$


USE `CampingSimulator`$$
DROP TRIGGER IF EXISTS `CampingSimulator`.`Product_AFTER_INSERT` $$
USE `CampingSimulator`$$
CREATE DEFINER = CURRENT_USER TRIGGER `CampingSimulator`.`Product_AFTER_INSERT` AFTER INSERT ON `Product` FOR EACH ROW
BEGIN
DECLARE userid INT;
    DECLARE notifid INT;
	DECLARE msg VARCHAR(200);
    DECLARE done INT DEFAULT FALSE;
	DECLARE cur CURSOR FOR SELECT User_id FROM User_Has_Authorization WHERE Authorization_label = 'PRODUCT_MANAGEMENT';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	
    IF NEW.stock < NEW.critical_quantity THEN
		
		SET msg = CONCAT('Le produit "', NEW.label, '" a atteint la quantité critique');
		INSERT INTO Notification(title,content) VALUES ('PRODUIT CRITIQUE', msg);
		
        SET notifid = LAST_INSERT_ID(); 
		
        OPEN cur;
        read_loop: LOOP
        
			FETCH cur into userid;
            IF done THEN
				LEAVE read_loop;
			END IF;
			
            INSERT INTO User_has_Notification(User_id, Notification_id) VALUES (userid, notifid);
            
        END LOOP;
    END IF;
END$$


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
