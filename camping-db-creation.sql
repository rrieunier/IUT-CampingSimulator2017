alter table CLIENT_PROBLEM drop foreign key FKF8FAE20B17117BC4
alter table CLIENT_PROBLEM drop foreign key FKF8FAE20B68BEE230
alter table EMPLOYEE_AUTHORIZATION drop foreign key FK6E325888C20E7E04
alter table EMPLOYEE_AUTHORIZATION drop foreign key FK6E325888866815D0
alter table EMPLOYEE_NOTIFICATION drop foreign key FK4ABACEDC866815D0
alter table EMPLOYEE_NOTIFICATION drop foreign key FK4ABACEDC6EDB35B0
alter table LOCATION_PROBLEM drop foreign key FK8E40FFF517117BC4
alter table LOCATION_PROBLEM drop foreign key FK8E40FFF59B04A470
alter table LOG drop foreign key FK12724866815D0
alter table PURCHASE drop foreign key FK968EF501902199C4
alter table PURCHASE drop foreign key FK968EF50168BEE230
alter table RESERVATION drop foreign key FK2328D7ACAAAFE050
alter table RESERVATION drop foreign key FK2328D7AC68BEE230
alter table RESTOCKING drop foreign key FKCA27583F902199C4
alter table RESTOCKING drop foreign key FKCA27583F8878D910
alter table SPOT drop foreign key FK26F1027A18F7A6
alter table SUPPLIER_PROPOSE_PRODUCT drop foreign key FK725A61BB902199C4
alter table SUPPLIER_PROPOSE_PRODUCT drop foreign key FK725A61BB8878D910
alter table TASK drop foreign key FK272D85866815D0
alter table TASK drop foreign key FK272D859B04A470
drop table if exists AUTHORIZATION
drop table if exists CLIENT
drop table if exists CLIENT_PROBLEM
drop table if exists EMPLOYEE
drop table if exists EMPLOYEE_AUTHORIZATION
drop table if exists EMPLOYEE_NOTIFICATION
drop table if exists LOCATION
drop table if exists LOCATION_PROBLEM
drop table if exists LOG
drop table if exists MAP
drop table if exists NOTIFICATION
drop table if exists PROBLEM
drop table if exists PRODUCT
drop table if exists PURCHASE
drop table if exists RESERVATION
drop table if exists RESTOCKING
drop table if exists SPOT
drop table if exists SUPPLIER
drop table if exists SUPPLIER_PROPOSE_PRODUCT
drop table if exists TASK
create table AUTHORIZATION (label varchar(45) not null, primary key (label))
create table CLIENT (id integer not null auto_increment, email varchar(45), firstname varchar(45) not null, lastname varchar(45) not null, phone varchar(11), primary key (id))
create table CLIENT_PROBLEM (client_id integer not null, problem_id integer not null, primary key (client_id, problem_id))
create table EMPLOYEE (id integer not null auto_increment, completeAddress varchar(255), email varchar(45), firstName varchar(255) not null, lastName varchar(255) not null, login varchar(20) unique, password varchar(255), phone varchar(11), primary key (id))
create table EMPLOYEE_AUTHORIZATION (employee_id integer not null, authorization_id varchar(45) not null, primary key (employee_id, authorization_id))
create table EMPLOYEE_NOTIFICATION (employee_id integer not null, notification_id integer not null, primary key (employee_id, notification_id))
create table LOCATION (id integer not null auto_increment, name varchar(255), pointX double precision not null, pointY double precision not null, primary key (id))
create table LOCATION_PROBLEM (location_id integer not null, problem_id integer not null, primary key (location_id, problem_id))
create table LOG (id integer not null auto_increment, action varchar(255) not null, datetime datetime not null, employee_id integer not null, primary key (id))
create table MAP (id integer not null auto_increment, image tinyblob not null, primary key (id))
create table NOTIFICATION (id integer not null auto_increment, content varchar(255) not null, title varchar(45) not null, primary key (id))
create table PROBLEM (id integer not null auto_increment, appearanceDatetime datetime not null, description varchar(255) not null, solutionDatetime datetime, state varchar(45) not null, primary key (id))
create table PRODUCT (id integer not null auto_increment, criticalQuantity integer not null, name varchar(255) not null, sellPrice float not null, stock integer not null, primary key (id))
create table PURCHASE (id integer not null auto_increment, datetime datetime not null, quantity integer not null, client_id integer not null, product_id integer not null, primary key (id))
create table RESERVATION (id integer not null auto_increment, clientComment varchar(255), endtime datetime not null, personCount integer not null, reservationDate datetime not null, starttime datetime not null, client_id integer not null, spot_id integer not null, primary key (id))
create table RESTOCKING (id integer not null auto_increment, datetime datetime not null, quantity integer not null, product_id integer not null, supplier_id integer not null, primary key (id))
create table SPOT (capacity integer not null, electricity bit not null, pricePerDay float not null, shadow bit not null, water bit not null, id integer not null, primary key (id))
create table SUPPLIER (id integer not null auto_increment, email varchar(255), name varchar(255) not null, phone varchar(255), website varchar(255), primary key (id))
create table SUPPLIER_PROPOSE_PRODUCT (id integer not null auto_increment, sellPrice float not null, product_id integer not null, supplier_id integer not null, primary key (id))
create table TASK (idTask integer not null auto_increment, endtime datetime not null, label varchar(255) not null, starttime datetime not null, employee_id integer not null, location_id integer, primary key (idTask))
alter table CLIENT_PROBLEM add index FKF8FAE20B17117BC4 (problem_id), add constraint FKF8FAE20B17117BC4 foreign key (problem_id) references PROBLEM (id)
alter table CLIENT_PROBLEM add index FKF8FAE20B68BEE230 (client_id), add constraint FKF8FAE20B68BEE230 foreign key (client_id) references CLIENT (id)
alter table EMPLOYEE_AUTHORIZATION add index FK6E325888C20E7E04 (authorization_id), add constraint FK6E325888C20E7E04 foreign key (authorization_id) references AUTHORIZATION (label)
alter table EMPLOYEE_AUTHORIZATION add index FK6E325888866815D0 (employee_id), add constraint FK6E325888866815D0 foreign key (employee_id) references EMPLOYEE (id)
alter table EMPLOYEE_NOTIFICATION add index FK4ABACEDC866815D0 (employee_id), add constraint FK4ABACEDC866815D0 foreign key (employee_id) references EMPLOYEE (id)
alter table EMPLOYEE_NOTIFICATION add index FK4ABACEDC6EDB35B0 (notification_id), add constraint FK4ABACEDC6EDB35B0 foreign key (notification_id) references NOTIFICATION (id)
alter table LOCATION_PROBLEM add index FK8E40FFF517117BC4 (problem_id), add constraint FK8E40FFF517117BC4 foreign key (problem_id) references PROBLEM (id)
alter table LOCATION_PROBLEM add index FK8E40FFF59B04A470 (location_id), add constraint FK8E40FFF59B04A470 foreign key (location_id) references LOCATION (id)
alter table LOG add index FK12724866815D0 (employee_id), add constraint FK12724866815D0 foreign key (employee_id) references EMPLOYEE (id)
alter table PURCHASE add index FK968EF501902199C4 (product_id), add constraint FK968EF501902199C4 foreign key (product_id) references PRODUCT (id)
alter table PURCHASE add index FK968EF50168BEE230 (client_id), add constraint FK968EF50168BEE230 foreign key (client_id) references CLIENT (id)
alter table RESERVATION add index FK2328D7ACAAAFE050 (spot_id), add constraint FK2328D7ACAAAFE050 foreign key (spot_id) references SPOT (id)
alter table RESERVATION add index FK2328D7AC68BEE230 (client_id), add constraint FK2328D7AC68BEE230 foreign key (client_id) references CLIENT (id)
alter table RESTOCKING add index FKCA27583F902199C4 (product_id), add constraint FKCA27583F902199C4 foreign key (product_id) references PRODUCT (id)
alter table RESTOCKING add index FKCA27583F8878D910 (supplier_id), add constraint FKCA27583F8878D910 foreign key (supplier_id) references SUPPLIER (id)
alter table SPOT add index FK26F1027A18F7A6 (id), add constraint FK26F1027A18F7A6 foreign key (id) references LOCATION (id)
alter table SUPPLIER_PROPOSE_PRODUCT add index FK725A61BB902199C4 (product_id), add constraint FK725A61BB902199C4 foreign key (product_id) references PRODUCT (id)
alter table SUPPLIER_PROPOSE_PRODUCT add index FK725A61BB8878D910 (supplier_id), add constraint FK725A61BB8878D910 foreign key (supplier_id) references SUPPLIER (id)
alter table TASK add index FK272D85866815D0 (employee_id), add constraint FK272D85866815D0 foreign key (employee_id) references EMPLOYEE (id)
alter table TASK add index FK272D859B04A470 (location_id), add constraint FK272D859B04A470 foreign key (location_id) references LOCATION (id)
