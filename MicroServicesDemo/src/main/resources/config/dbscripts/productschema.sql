drop table product if exists;

create table Product (
  product_id bigint identity primary key,
  product_type VARCHAR(255) not null,
  product_name VARCHAR(255) not null
  );