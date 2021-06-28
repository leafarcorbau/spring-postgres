CREATE TABLE books (
    id UUID PRIMARY KEY,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    title TEXT,
    author VARCHAR(100),
    genre VARCHAR(10),
    copies_count INTEGER
);

CREATE TRIGGER books_insert_timestamp BEFORE INSERT ON books FOR EACH ROW EXECUTE PROCEDURE insert_timestamps();
CREATE TRIGGER books_update_timestamp BEFORE UPDATE ON books FOR EACH ROW EXECUTE PROCEDURE update_timestamps();

CREATE INDEX udx_books_title ON books(title);
CREATE INDEX udx_books_author ON books(author);