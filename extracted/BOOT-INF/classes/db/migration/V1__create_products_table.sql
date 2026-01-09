CREATE TABLE products (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    category TEXT NOT NULL,
    brand TEXT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    image_url TEXT NOT NULL,
    rating DOUBLE PRECISION,
    rating_count INTEGER,
    stock INTEGER,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
