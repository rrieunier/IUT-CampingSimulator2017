alter table SPOT drop foreign key FK26F102F5311443
drop table if exists AUTHORIZATION
drop table if exists CLIENT
drop table if exists EMPLOYEE
drop table if exists LOCATION
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
drop table if exists TASK
create table AUTHORIZATION (label varchar(45) not null, primary key (label))
create table CLIENT (id integer not null auto_increment, email varchar(45), firstname varchar(45) not null, lastname varchar(45) not null, phone varchar(11), primary key (id))
create table EMPLOYEE (id integer not null auto_increment, completeAddress varchar(255), email varchar(45), firstName varchar(255) not null, lastName varchar(255) not null, login varchar(20) unique, password varchar(255), phone varchar(11), primary key (id))
create table LOCATION (id integer not null auto_increment, name varchar(255), pointX double precision not null, pointY double precision not null, primary key (id))
create table LOG (id integer not null auto_increment, action varchar(255) not null, datetime datetime not null, primary key (id))
create table MAP (id integer not null auto_increment, image tinyblob not null, primary key (id))
create table NOTIFICATION (id integer not null auto_increment, content varchar(255) not null, title varchar(45) not null, primary key (id))
create table PROBLEM (id integer not null auto_increment, appearanceDatetime datetime not null, description varchar(255) not null, solutionDatetime datetime, state varchar(45) not null, primary key (id))
create table PRODUCT (id integer not null auto_increment, criticalQuantity integer not null, name varchar(255) not null, sellPrice float not null, stock integer not null, primary key (id))
create table PURCHASE (id integer not null auto_increment, datetime datetime not null, quantity integer not null, primary key (id))
create table RESERVATION (id integer not null auto_increment, clientComment varchar(255), endtime datetime not null, personCount integer not null, reservationDate datetime not null, starttime datetime not null, primary key (id))
create table RESTOCKING (id integer not null auto_increment, datetime datetime not null, quantity integer not null, primary key (id))
create table SPOT (capacity integer not null, electricity bit not null, pricePerDay float not null, shadow bit not null, water bit not null, id integer not null, primary key (id))
create table SUPPLIER (id integer not null auto_increment, email varchar(255), name varchar(255) not null, phone varchar(255), website varchar(255), primary key (id))
create table TASK (idTask integer not null auto_increment, endtime datetime not null, label varchar(255) not null, starttime datetime not null, primary key (idTask))
alter table SPOT add index FK26F102F5311443 (id), add constraint FK26F102F5311443 foreign key (id) references LOCATION (id)
