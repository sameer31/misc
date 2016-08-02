drop table pricing if exists;

create table Pricing (
  price_id bigint identity primary key,
  product_name VARCHAR(255) not null,
  product_price INTEGER not null
  );
