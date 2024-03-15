-- Create the users table if it does not exist
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL
    );

-- Create the token table if it does not exist
CREATE TABLE IF NOT EXISTS token (
    id BIGSERIAL PRIMARY KEY,
    token_name VARCHAR(255) UNIQUE,
    is_expired BOOLEAN,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
    );

-- Create the movies table if it does not exist
CREATE TABLE IF NOT EXISTS movies (
    imdb_id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year VARCHAR(4) NOT NULL,
    type VARCHAR(255) NOT NULL,
    poster VARCHAR(255) NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_imdb_id ON movies (imdb_id);

-- Create the comments table if it does not exist
CREATE TABLE IF NOT EXISTS comment (
    id BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    timestamp DATE NOT NULL,
    movie_imdb_id VARCHAR(255),
    FOREIGN KEY (movie_imdb_id) REFERENCES movies (imdb_id)
    );

-- Create the favorite_movie table if it does not exist
CREATE TABLE IF NOT EXISTS favorite_movies (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (movie_id) REFERENCES movies (imdb_id) ON DELETE CASCADE
    );

ALTER TABLE favorite_movies ADD CONSTRAINT unique_user_movie UNIQUE (user_id, movie_id);