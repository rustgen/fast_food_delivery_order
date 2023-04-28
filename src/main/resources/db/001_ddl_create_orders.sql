create TABLE IF NOT EXISTS orders (
  id serial primary key,
  total_price double precision NOT NULL,
  is_completed BOOLEAN NOT NULL
);
