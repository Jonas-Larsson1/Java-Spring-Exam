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

INSERT INTO roles (name) VALUES
    ('ROLE_MEMBER'),
    ('ROLE_LIBRARIAN'),
    ('ROLE_ADMIN');

-- Populate users
INSERT INTO users (first_name, last_name, email, password, member_number, role_id) VALUES
    ('Anna', 'Andersson', 'anna.andersson@email.com',  '$2a$10$dOUdi8LLYSt3Ac5YvIDWCOo.fIzDWJW89Ivcshz/qFotxdVb3E0ly', 10000001, 3),
    ('Erik', 'Eriksson', 'erik.eriksson@email.com',  '$2a$10$dOUdi8LLYSt3Ac5YvIDWCOo.fIzDWJW89Ivcshz/qFotxdVb3E0ly', 10000002, 2),
    ('Maria', 'Svensson', 'maria.svensson@email.com', '$2a$10$dOUdi8LLYSt3Ac5YvIDWCOo.fIzDWJW89Ivcshz/qFotxdVb3E0ly', 10000003, 1),
    ('Johan', 'Johansson', 'johan.johansson@email.com', '$2a$10$dOUdi8LLYSt3Ac5YvIDWCOo.fIzDWJW89Ivcshz/qFotxdVb3E0ly', 10000004, 1),
    ('Eva', 'Larsson', 'eva.larsson@email.com', '$2a$10$dOUdi8LLYSt3Ac5YvIDWCOo.fIzDWJW89Ivcshz/qFotxdVb3E0ly', 10000005, 1);

-- Populate loans
INSERT INTO loans (book_id, user_id, loan_date, due_date, returned_date) VALUES
    (2, 1, '2024-01-15', '2024-02-15', NULL),
    (5, 2, '2024-01-20', '2024-02-20', NULL),
    (9, 3, '2024-01-25', '2024-02-25', NULL),
    (1, 4, '2023-12-15', '2024-01-15', '2024-01-14'),
    (3, 5, '2023-12-20', '2024-01-20', '2024-01-18'),
    (6, 1, '2023-12-25', '2024-01-25', '2024-01-23');