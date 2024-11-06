--CREATE TABLE IF NOT EXISTS authors (
--    author_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    first_name VARCHAR(100) NOT NULL,
--    last_name VARCHAR(100) NOT NULL,
--    birth_date DATE
--);
--
--CREATE TABLE IF NOT EXISTS books (
--    book_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    title VARCHAR(255) NOT NULL,
--    publication_year INT,
--    author_id BIGINT,
--    available BOOLEAN DEFAULT TRUE,
--    FOREIGN KEY (author_id) REFERENCES authors(author_id)
--);
--
--CREATE TABLE IF NOT EXISTS genres (
--    genre_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    name VARCHAR(50) NOT NULL UNIQUE
--);
--
--CREATE TABLE IF NOT EXISTS books_genres (
--    book_id BIGINT,
--    genre_id BIGINT,
--    PRIMARY KEY (book_id, genre_id),
--    FOREIGN KEY (book_id) REFERENCES books(book_id),
--    FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
--);
--
--CREATE TABLE IF NOT EXISTS members (
--    member_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    first_name VARCHAR(100) NOT NULL,
--    last_name VARCHAR(100) NOT NULL,
--    email VARCHAR(255) NOT NULL UNIQUE,
--    member_number VARCHAR(10) NOT NULL UNIQUE
--);
--
--CREATE TABLE IF NOT EXISTS loans (
--    loan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    book_id BIGINT,
--    member_id BIGINT,
--    loan_date DATE NOT NULL,
--    due_date DATE NOT NULL,
--    returned_date DATE,
--    FOREIGN KEY (book_id) REFERENCES books(book_id),
--    FOREIGN KEY (member_id) REFERENCES members(member_id)
--);
--
----CREATE TABLE IF NOT EXISTS admins (
----    admin_id BIGINT PRIMARY KEY AUTO_INCREMENT,
----    username VARCHAR(50) NOT NULL UNIQUE,
----    password VARCHAR(50) NOT NULL,
----    role VARCHAR(20) NOT NULL
----);
--
---- Clear existing data
--DELETE FROM books_genres;
--DELETE FROM genres;
--DELETE FROM loans;
--DELETE FROM books;
--DELETE FROM authors;
--DELETE FROM members;
----DELETE FROM admins;
--
---- Reset auto-increment counters
--ALTER TABLE loans ALTER COLUMN loan_id RESTART WITH 1;
--ALTER TABLE books ALTER COLUMN book_id RESTART WITH 1;
--ALTER TABLE authors ALTER COLUMN author_id RESTART WITH 1;
--ALTER TABLE members ALTER COLUMN member_id RESTART WITH 1;
----ALTER TABLE admins ALTER COLUMN admin_id RESTART WITH 1;
--ALTER TABLE genres ALTER COLUMN genre_id RESTART WITH 1;

-- Populate authors
INSERT INTO authors (first_name, last_name, birth_date) VALUES
    ('Astrid', 'Lindgren', '1907-11-14'),
    ('J.K.', 'Rowling', '1965-07-31'),
    ('Stephen', 'King', '1947-09-21'),
    ('Stieg', 'Larsson', '1954-08-15'),
    ('Virginia', 'Woolf', '1882-01-25');

-- Populate books
INSERT INTO books (title, publication_year, author_id, available) VALUES
    ('Pippi Långstrump', 1945, 1, true),
    ('Bröderna Lejonhjärta', 1973, 1, false),
    ('Harry Potter and the Philosopher''s Stone', 1997, 2, true),
    ('Harry Potter and the Chamber of Secrets', 1998, 2, true),
    ('Harry Potter and the Prisoner of Azkaban', 1999, 2, false),
    ('The Shining', 1977, 3, true),
    ('Pet Sematary', 1983, 3, true),
    ('Män som hatar kvinnor', 2005, 4, true),
    ('Flickan som lekte med elden', 2006, 4, false),
    ('Mrs. Dalloway', 1925, 5, true);

-- Populate genres
INSERT INTO genres (name) VALUES
    ('Fantasy'),
    ('Horror'),
    ('Crime'),
    ('Classic'),
    ('Children');

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
INSERT INTO members (first_name, last_name, email, member_number) VALUES
    ('Anna', 'Andersson', 'anna.andersson@email.com', 'M20230001'),
    ('Erik', 'Eriksson', 'erik.eriksson@email.com', 'M20230002'),
    ('Maria', 'Svensson', 'maria.svensson@email.com', 'M20230003'),
    ('Johan', 'Johansson', 'johan.johansson@email.com', 'M20230004'),
    ('Eva', 'Larsson', 'eva.larsson@email.com', 'M20230005');

-- Populate loans
INSERT INTO loans (book_id, member_id, loan_date, due_date, returned_date) VALUES
    (2, 1, '2024-01-15', '2024-02-15', NULL),
    (5, 2, '2024-01-20', '2024-02-20', NULL),
    (9, 3, '2024-01-25', '2024-02-25', NULL),
    (1, 4, '2023-12-15', '2024-01-15', '2024-01-14'),
    (3, 5, '2023-12-20', '2024-01-20', '2024-01-18'),
    (6, 1, '2023-12-25', '2024-01-25', '2024-01-23');

---- Populate admins
--INSERT INTO admins (admin_id, username, password, role) VALUES
--    (1, 'admin', 'admin123', 'ADMIN'),
--    (2, 'lisa', 'lisa123', 'LIBRARIAN'),
--    (3, 'lars', 'lars123', 'LIBRARIAN');