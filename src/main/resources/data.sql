CREATE TABLE IF NOT EXISTS authors (
    author_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE
);

CREATE TABLE IF NOT EXISTS books (
    book_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    publication_year INT,
    author_id BIGINT,
    available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id)
);

CREATE TABLE IF NOT EXISTS genres (
    genre_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS books_genres (
    book_id BIGINT,
    genre_id BIGINT,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    member_number VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS loans (
    loan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id BIGINT,
    user_id BIGINT,
    loan_date DATE NOT NULL,
    due_date DATE NOT NULL,
    returned_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS admins (
    admin_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Clear existing data
DELETE FROM books_genres;
DELETE FROM genres;
DELETE FROM loans;
DELETE FROM books;
DELETE FROM authors;
DELETE FROM users;
DELETE FROM admins;

-- Reset auto-increment counters
ALTER TABLE loans ALTER COLUMN loan_id RESTART WITH 1;
ALTER TABLE books ALTER COLUMN book_id RESTART WITH 1;
ALTER TABLE authors ALTER COLUMN author_id RESTART WITH 1;
ALTER TABLE users ALTER COLUMN user_id RESTART WITH 1;
ALTER TABLE admins ALTER COLUMN admin_id RESTART WITH 1;
ALTER TABLE genres ALTER COLUMN genre_id RESTART WITH 1;

-- Populate authors
INSERT INTO authors (author_id, first_name, last_name, birth_date) VALUES
    (1, 'Astrid', 'Lindgren', '1907-11-14'),
    (2, 'J.K.', 'Rowling', '1965-07-31'),
    (3, 'Stephen', 'King', '1947-09-21'),
    (4, 'Stieg', 'Larsson', '1954-08-15'),
    (5, 'Virginia', 'Woolf', '1882-01-25');

-- Populate books
INSERT INTO books (book_id, title, publication_year, author_id, available) VALUES
    (1, 'Pippi Långstrump', 1945, 1, true),
    (2, 'Bröderna Lejonhjärta', 1973, 1, false),
    (3, 'Harry Potter and the Philosopher''s Stone', 1997, 2, true),
    (4, 'Harry Potter and the Chamber of Secrets', 1998, 2, true),
    (5, 'Harry Potter and the Prisoner of Azkaban', 1999, 2, false),
    (6, 'The Shining', 1977, 3, true),
    (7, 'Pet Sematary', 1983, 3, true),
    (8, 'Män som hatar kvinnor', 2005, 4, true),
    (9, 'Flickan som lekte med elden', 2006, 4, false),
    (10, 'Mrs. Dalloway', 1925, 5, true);

-- Populate genres
INSERT INTO genres (genre_id, name) VALUES
    (1, 'Fantasy'),
    (2, 'Horror'),
    (3, 'Crime'),
    (4, 'Classic'),
    (5, 'Children');

-- Connect books with genres (many-to-many)
INSERT INTO books_genres (book_id, genre_id) VALUES
    -- Pippi och Bröderna Lejonhjärta: Children, Fantasy
    (1, 5),
    (1, 1),
    (2, 5),
    (2, 1),
    -- Harry Potter böckerna: Fantasy
    (3, 1),
    (4, 1),
    (5, 1),
    -- King's böcker: Horror
    (6, 2),
    (7, 2),
    -- Millennium böckerna: Crime
    (8, 3),
    (9, 3),
    -- Mrs. Dalloway: Classic
    (10, 4);

-- Populate users
INSERT INTO users (user_id, first_name, last_name, email, member_number) VALUES
    (1, 'Anna', 'Andersson', 'anna.andersson@email.com', 'M20230001'),
    (2, 'Erik', 'Eriksson', 'erik.eriksson@email.com', 'M20230002'),
    (3, 'Maria', 'Svensson', 'maria.svensson@email.com', 'M20230003'),
    (4, 'Johan', 'Johansson', 'johan.johansson@email.com', 'M20230004'),
    (5, 'Eva', 'Larsson', 'eva.larsson@email.com', 'M20230005');

-- Populate loans
INSERT INTO loans (loan_id, book_id, user_id, loan_date, due_date, returned_date) VALUES
    (1, 2, 1, '2024-01-15', '2024-02-15', NULL),
    (2, 5, 2, '2024-01-20', '2024-02-20', NULL),
    (3, 9, 3, '2024-01-25', '2024-02-25', NULL),
    (4, 1, 4, '2023-12-15', '2024-01-15', '2024-01-14'),
    (5, 3, 5, '2023-12-20', '2024-01-20', '2024-01-18'),
    (6, 6, 1, '2023-12-25', '2024-01-25', '2024-01-23');

-- Populate admins
INSERT INTO admins (admin_id, username, password, role) VALUES
    (1, 'admin', 'admin123', 'ADMIN'),
    (2, 'lisa', 'lisa123', 'LIBRARIAN'),
    (3, 'lars', 'lars123', 'LIBRARIAN');