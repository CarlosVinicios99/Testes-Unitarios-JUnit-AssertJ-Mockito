CREATE TABLE products (
	product_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	name VARCHAR(100) NOT NULL,
	price NUMERIC(10, 2) NOT NULL,
	description VARCHAR(255)
);