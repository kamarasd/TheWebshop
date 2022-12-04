create table catalog_aud (id int8 not null, rev int4 not null, revtype int2, product_name varchar(255), product_price int4, category_id int8, primary key (id, rev));
create table category_aud (id int8 not null, rev int4 not null, revtype int2, category_name varchar(255), primary key (id, rev));
create table revinfo (rev int4 not null, revtstmp int8, primary key (rev));
alter table if exists catalog_aud add constraint FK_catalog_aud foreign key (rev) references revinfo;
alter table if exists category_aud add constraint FKcategory_aud foreign key (rev) references revinfo;