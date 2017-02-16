alter table EMPLOYEE_AUTHORIZATION drop foreign key FK6E3258884990BB87
alter table EMPLOYEE_AUTHORIZATION drop foreign key FK6E325888180326D
alter table EMPLOYEE_NOTIFICATION drop foreign key FK4ABACEDC180326D
alter table EMPLOYEE_NOTIFICATION drop foreign key FK4ABACEDC3129BBCD
alter table LOG drop foreign key FK12724180326D
alter table SPOT drop foreign key FK26F102F5311443
alter table TASK drop foreign key FK272D85180326D
drop table if exists AUTHORIZATION
drop table if exists CLIENT
drop table if exists EMPLOYEE
drop table if exists EMPLOYEE_AUTHORIZATION
drop table if exists EMPLOYEE_NOTIFICATION
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
create table EMPLOYEE_AUTHORIZATION (employee_id integer not null, authorization_id varchar(45) not null, primary key (employee_id, authorization_id))
create table EMPLOYEE_NOTIFICATION (employee_id integer not null, notification_id integer not null, primary key (employee_id, notification_id))
create table LOCATION (id integer not null auto_increment, name varchar(255), pointX double precision not null, pointY double precision not null, primary key (id))
create table LOG (id integer not null auto_increment, action varchar(255) not null, datetime datetime not null, employee_id integer not null, primary key (id))
create table MAP (id integer not null auto_increment, image tinyblob not null, primary key (id))
create table NOTIFICATION (id integer not null auto_increment, content varchar(255) not null, title varchar(45) not null, primary key (id))
create table PROBLEM (id integer not null auto_increment, appearanceDatetime datetime not null, description varchar(255) not null, solutionDatetime datetime, state varchar(45) not null, primary key (id))
create table PRODUCT (id integer not null auto_increment, criticalQuantity integer not null, name varchar(255) not null, sellPrice float not null, stock integer not null, primary key (id))
create table PURCHASE (id integer not null auto_increment, datetime datetime not null, quantity integer not null, primary key (id))
create table RESERVATION (id integer not null auto_increment, clientComment varchar(255), endtime datetime not null, personCount integer not null, reservationDate datetime not null, starttime datetime not null, primary key (id))
create table RESTOCKING (id integer not null auto_increment, datetime datetime not null, quantity integer not null, primary key (id))
create table SPOT (capacity integer not null, electricity bit not null, pricePerDay float not null, shadow bit not null, water bit not null, id integer not null, primary key (id))
create table SUPPLIER (id integer not null auto_increment, email varchar(255), name varchar(255) not null, phone varchar(255), website varchar(255), primary key (id))
create table TASK (idTask integer not null auto_increment, endtime datetime not null, label varchar(255) not null, starttime datetime not null, employee_id integer not null, primary key (idTask))
alter table EMPLOYEE_AUTHORIZATION add index FK6E3258884990BB87 (authorization_id), add constraint FK6E3258884990BB87 foreign key (authorization_id) references AUTHORIZATION (label)
alter table EMPLOYEE_AUTHORIZATION add index FK6E325888180326D (employee_id), add constraint FK6E325888180326D foreign key (employee_id) references EMPLOYEE (id)
alter table EMPLOYEE_NOTIFICATION add index FK4ABACEDC180326D (employee_id), add constraint FK4ABACEDC180326D foreign key (employee_id) references EMPLOYEE (id)
alter table EMPLOYEE_NOTIFICATION add index FK4ABACEDC3129BBCD (notification_id), add constraint FK4ABACEDC3129BBCD foreign key (notification_id) references NOTIFICATION (id)
alter table LOG add index FK12724180326D (employee_id), add constraint FK12724180326D foreign key (employee_id) references EMPLOYEE (id)
alter table SPOT add index FK26F102F5311443 (id), add constraint FK26F102F5311443 foreign key (id) references LOCATION (id)
alter table TASK add index FK272D85180326D (employee_id), add constraint FK272D85180326D foreign key (employee_id) references EMPLOYEE (id)
