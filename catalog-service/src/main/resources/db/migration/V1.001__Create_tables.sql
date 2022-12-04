create sequence hibernate_sequence start 1 increment 1;
create table catalog (id int8 not null, product_name varchar(255), product_price int4 not null, category_id int8, primary key (id));
create table category (id int8 not null, category_name varchar(255), primary key (id));
alter table if exists catalog add constraint FK4e0jibokb2403yseeqvt618wh foreign key (category_id) references category;
