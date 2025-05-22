CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

INSERT INTO products (name, description, price, quantity) VALUES
('Laptop', '14-inch laptop with 16GB RAM and 512GB SSD', 999.99, 10),
('Smartphone', '5G Android phone with 128GB storage', 699.00, 25),
('Wireless Mouse', 'Ergonomic mouse with 2.4GHz USB receiver', 24.99, 100),
('Mechanical Keyboard', 'RGB mechanical keyboard with blue switches', 89.50, 40);