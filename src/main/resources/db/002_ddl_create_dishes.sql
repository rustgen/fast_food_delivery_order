create TABLE IF NOT EXISTS dish (
  id serial primary key,
  name VARCHAR,
  description VARCHAR,
  price double precision NOT NULL,
  order_id int REFERENCES orders(id)
);