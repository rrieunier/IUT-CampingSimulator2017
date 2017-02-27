  DROP DATABASE IF EXISTS Camping;
  CREATE DATABASE Camping;
  DROP USER IF EXISTS camping;
  CREATE USER IF NOT EXISTS camping IDENTIFIED BY 'camping';
  USE Camping;

    create table AUTHORIZATION (
       label varchar(45) not null,
        primary key (label)
    );

    create table CLIENT (
       id integer not null,
        email varchar(45),
        firstname varchar(45) not null,
        lastname varchar(45) not null,
        phone varchar(11),
        primary key (id)
    );

    create table CLIENT_PROBLEM (
       client_id integer not null,
        problem_id integer not null,
        primary key (client_id, problem_id)
    );

    create table EMPLOYEE (
       id integer not null,
        completeAddress varchar(255),
        email varchar(45),
        firstName varchar(255) not null,
        lastName varchar(255) not null,
        login varchar(20),
        password varchar(255),
        phone varchar(11),
        primary key (id)
    );

    create table EMPLOYEE_AUTHORIZATION (
       employee_id integer not null,
        authorization_id varchar(45) not null,
        primary key (employee_id, authorization_id)
    );

    create table EMPLOYEE_NOTIFICATION (
       employee_id integer not null,
        notification_id integer not null,
        primary key (employee_id, notification_id)
    );

    create table hibernate_sequence (
       next_val bigint
    );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    create table LOCATION (
       id integer not null,
        name varchar(255),
        pointX double precision not null,
        pointY double precision not null,
        primary key (id)
    );

    create table LOCATION_PROBLEM (
       location_id integer not null,
        problem_id integer not null,
        primary key (location_id, problem_id)
    );

    create table LOG (
       id integer not null,
        action varchar(255) not null,
        datetime datetime not null,
        employee_id integer not null,
        primary key (id)
    );

    create table MAP (
       id integer not null,
        image tinyblob not null,
        primary key (id)
    );

    create table NOTIFICATION (
       id integer not null,
        content varchar(255) not null,
        title varchar(45) not null,
        primary key (id)
    );

    create table PROBLEM (
       id integer not null,
        appearanceDatetime datetime not null,
        description varchar(255) not null,
        solutionDatetime datetime,
        state varchar(45) not null,
        primary key (id)
    );

    create table PRODUCT (
       id integer not null,
        criticalQuantity integer not null,
        name varchar(255) not null,
        sellPrice float not null,
        stock integer not null,
        primary key (id)
    );

    create table PURCHASE (
       id integer not null,
        datetime datetime not null,
        quantity integer not null,
        client_id integer not null,
        product_id integer not null,
        primary key (id)
    );

    create table RESERVATION (
       id integer not null,
        clientComment varchar(255),
        endtime datetime not null,
        personCount integer not null,
        reservationDate datetime not null,
        starttime datetime not null,
        client_id integer not null,
        spot_id integer not null,
        primary key (id)
    );

    create table RESTOCKING (
       id integer not null,
        datetime datetime not null,
        quantity integer not null,
        product_id integer not null,
        supplier_id integer not null,
        primary key (id)
    );

    create table SPOT (
       capacity integer not null,
        electricity bit not null,
        pricePerDay float not null,
        shadow bit not null,
        water bit not null,
        id integer not null,
        primary key (id)
    );

    create table SUPPLIER (
       id integer not null,
        email varchar(255),
        name varchar(255) not null,
        phone varchar(255),
        website varchar(255),
        primary key (id)
    );

    create table SUPPLIER_PROPOSE_PRODUCT (
       id integer not null,
        sellPrice float not null,
        product_id integer not null,
        supplier_id integer not null,
        primary key (id)
    );

    create table TASK (
       id integer not null,
        endtime datetime not null,
        label varchar(255) not null,
        starttime datetime not null,
        employee_id integer not null,
        location_id integer,
        primary key (id)
    );

    alter table EMPLOYEE 
       add constraint UK_7v1f89g44sb8vhqy4ok2bispy unique (login);

    alter table CLIENT_PROBLEM 
       add constraint FKaro9e5vltrvgj9fvdhcf0pr7e 
       foreign key (problem_id) 
       references PROBLEM (id);

    alter table CLIENT_PROBLEM 
       add constraint FKewlxful95rewo1oo2ank3wlhk 
       foreign key (client_id) 
       references CLIENT (id);

    alter table EMPLOYEE_AUTHORIZATION 
       add constraint FKev1uq9wohdkyukiywc9kbqe8a 
       foreign key (authorization_id) 
       references AUTHORIZATION (label);

    alter table EMPLOYEE_AUTHORIZATION 
       add constraint FKarrcc34wiu2p4gb2s2a0d4a3d 
       foreign key (employee_id) 
       references EMPLOYEE (id);

    alter table EMPLOYEE_NOTIFICATION 
       add constraint FKqengm5re9clbresmu9n44b54r 
       foreign key (notification_id) 
       references NOTIFICATION (id);

    alter table EMPLOYEE_NOTIFICATION 
       add constraint FKagpevu1qohk7ofiwo2mq37ocv 
       foreign key (employee_id) 
       references EMPLOYEE (id);

    alter table LOCATION_PROBLEM 
       add constraint FK79huk0l77wvmuuq8dyupf70jb 
       foreign key (problem_id) 
       references PROBLEM (id);

    alter table LOCATION_PROBLEM 
       add constraint FK36wlscpqnnu7hfj0v9h5vybjj 
       foreign key (location_id) 
       references LOCATION (id);

    alter table LOG 
       add constraint FKlraml1cyoakihshtt0d2fwyem 
       foreign key (employee_id) 
       references EMPLOYEE (id);

    alter table PURCHASE 
       add constraint FKqgsswa6fm68e01npu6x82i4ys 
       foreign key (client_id) 
       references CLIENT (id);

    alter table PURCHASE 
       add constraint FKequxp5ih1ypinp91jtaaqp0jp 
       foreign key (product_id) 
       references PRODUCT (id);

    alter table RESERVATION 
       add constraint FK9fe9ikuq3uj2ivu2kj46k0pia 
       foreign key (client_id) 
       references CLIENT (id);

    alter table RESERVATION 
       add constraint FK83mmgw4wy7137q65sf0tvged8 
       foreign key (spot_id) 
       references SPOT (id);

    alter table RESTOCKING 
       add constraint FKlamw26gpk37jo3y5sxqu6q8jv 
       foreign key (product_id) 
       references PRODUCT (id);

    alter table RESTOCKING 
       add constraint FK88d4f0eol61ikqrf8s56hh96e 
       foreign key (supplier_id) 
       references SUPPLIER (id);

    alter table SPOT 
       add constraint FKj92gxury8y6ro66t6310gxhyg 
       foreign key (id) 
       references LOCATION (id);

    alter table SUPPLIER_PROPOSE_PRODUCT 
       add constraint FKioers9ycmbonyn3ylj3u950sf 
       foreign key (product_id) 
       references PRODUCT (id);

    alter table SUPPLIER_PROPOSE_PRODUCT 
       add constraint FK6wr2xdori660ehg2hsxxmxopl 
       foreign key (supplier_id) 
       references SUPPLIER (id);

    alter table TASK 
       add constraint FKcjcuvxpetiu28vecbdpiriwd5 
       foreign key (employee_id) 
       references EMPLOYEE (id);

    alter table TASK 
       add constraint FK9y4w10ihe6vms9vgb9th4n7na 
       foreign key (location_id) 
       references LOCATION (id);

  GRANT SELECT, INSERT, UPDATE, DELETE ON Camping.* TO 'camping'@'%';

