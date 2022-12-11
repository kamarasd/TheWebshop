create sequence hibernate_sequence start 1 increment 1;
create table address (id int8 not null, city_name varchar(255), house_no varchar(255), street varchar(255), zip_code varchar(255), primary key (id));
create table order (id int8 not null, item varchar(255), order_status int4, piece int4, price int4, user_id int8, address_id int8, primary key (id));
alter table if exists order add constraint FKikii9cvwfi5whtmawuek3joxo foreign key (address_id) references address;
create sequence hibernate_sequence start 1 increment 1;
create table address (id int8 not null, city_name varchar(255), house_no varchar(255), street varchar(255), zip_code varchar(255), primary key (id));
create table order (id int8 not null, item varchar(255), order_status int4, piece int4, price int4, user_id int8, address_id int8, primary key (id));
alter table if exists order add constraint FKikii9cvwfi5whtmawuek3joxo foreign key (address_id) references address;
