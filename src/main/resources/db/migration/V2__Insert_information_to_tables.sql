-- password is 'password'
-- INSERT data to users table
INSERT INTO users (user_name, password, email, role)
VALUES
    ('vlad', '$2a$10$TukJyoZnLoZBpK9xewiNGeWexXntRR/Ov7KjVVzy0RLbbDLziwEG.', 'vlad@gmail.com', 'ROLE_USER'),
    ('admin1', '$2a$10$TukJyoZnLoZBpK9xewiNGeWexXntRR/Ov7KjVVzy0RLbbDLziwEG.', 'admin1@example.com', 'ROLE_ADMIN');

-- INSERT data to movies table
INSERT INTO movies (imdb_id, title, year, type, poster)
VALUES ('tt1490017', 'The Lego Movie', '2014', 'movie', 'https://m.media-amazon.com/images/M/MV5BMTg4MDk1ODExN15BMl5BanBnXkFtZTgwNzIyNjg3MDE@._V1_SX300.jpg'),
       ('tt0462538', 'The Simpsons Movie', '2007', 'movie', 'https://m.media-amazon.com/images/M/MV5BNjc4NmQyNGUtMDg4NS00ZTZkLWI3ODQtMGJmYThiYjQxNGRiXkEyXkFqcGdeQXVyMTA0MTM5NjI2._V1_SX300.jpg'),
       ('tt9243946', 'El Camino: A Breaking Bad Movie', '2019', 'movie', 'https://m.media-amazon.com/images/M/MV5BNjk4MzVlM2UtZGM0ZC00M2M1LThkMWEtZjUyN2U2ZTc0NmM5XkEyXkFqcGdeQXVyOTAzMTc2MjA@._V1_SX300.jpg'),
       ('tt0175142', 'Scary Movie', '2000', 'movie', 'https://m.media-amazon.com/images/M/MV5BMGEzZjdjMGQtZmYzZC00N2I4LThiY2QtNWY5ZmQ3M2ExZmM4XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg'),
       ('tt6718170', 'The Super Mario Bros. Movie', '2023', 'movie', 'https://m.media-amazon.com/images/M/MV5BOTJhNzlmNzctNTU5Yy00N2YwLThhMjQtZDM0YjEzN2Y0ZjNhXkEyXkFqcGdeQXVyMTEwMTQ4MzU5._V1_SX300.jpg'),
       ('tt0389790', 'Bee Movie', '2007', 'movie', 'https://m.media-amazon.com/images/M/MV5BMjE1MDYxOTA4MF5BMl5BanBnXkFtZTcwMDE0MDUzMw@@._V1_SX300.jpg'),
       ('tt0257106', 'Scary Movie 2', '2001', 'movie', 'https://m.media-amazon.com/images/M/MV5BMzQxYjU1OTUtYjRiOC00NDg2LWI4MWUtZGU5YzdkYTcwNTBlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg'),
       ('tt4116284', 'The Lego Batman Movie', '2017', 'movie', 'https://m.media-amazon.com/images/M/MV5BMTcyNTEyOTY0M15BMl5BanBnXkFtZTgwOTAyNzU3MDI@._V1_SX300.jpg'),
       ('tt0306047', 'Scary Movie 3', '2003', 'movie', 'https://m.media-amazon.com/images/M/MV5BNDE2NTIyMjg2OF5BMl5BanBnXkFtZTYwNDEyMTg3._V1_SX300.jpg'),
       ('tt0362120', 'Scary Movie 4', '2006', 'movie', 'https://m.media-amazon.com/images/M/MV5BZmFkMzc2NTctN2U1Ni00MzE5LWJmMzMtYWQ4NjQyY2MzYmM1XkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_SX300.jpg');

-- Insert comments
INSERT INTO comment (author_id, text, timestamp, movie_imdb_id)
VALUES (1, 'Great movie, highly recommended!', '2023-03-14', 'tt1490017'),
       (2, 'One of my favorite movies!', '2023-03-14', 'tt1490017'),
       (1, 'Hilarious!', '2023-03-14', 'tt0462538'),
       (2, 'Classic!', '2023-03-14', 'tt0462538'),
       (1, 'Intense and captivating storyline.', '2023-03-14', 'tt9243946'),
       (2, 'Loved it!', '2023-03-14', 'tt9243946'),
       (1, 'Funny and clever!', '2023-03-14', 'tt0175142'),
       (2, 'Entertaining!', '2023-03-14', 'tt0175142'),
       (1, 'Awesome!', '2023-03-14', 'tt6718170'),
       (2, 'A must-watch for Mario fans!', '2023-03-14', 'tt6718170'),
       (1, 'Un-bee-lievable!', '2023-03-14', 'tt0389790'),
       (2, 'Bee-tastic!', '2023-03-14', 'tt0389790'),
       (1, 'So funny, I can''t stop laughing!', '2023-03-14', 'tt0257106'),
       (2, 'Hilarious sequel!', '2023-03-14', 'tt0257106'),
       (1, 'Awesome movie!', '2023-03-14', 'tt4116284'),
       (2, 'Loved every minute of it!', '2023-03-14', 'tt4116284'),
       (1, 'Scary and hilarious!', '2023-03-14', 'tt0306047'),
       (2, 'Great comedy!', '2023-03-14', 'tt0306047'),
       (1, 'Enjoyed it!', '2023-03-14', 'tt0362120'),
       (2, 'Good movie!', '2023-03-14', 'tt0362120');

-- Insert favorite movies
INSERT INTO favorite_movies (user_id, movie_id)
VALUES  (1, 'tt1490017'),
        (2, 'tt0462538'),
        (1, 'tt9243946'),
        (2, 'tt0175142'),
        (1, 'tt6718170'),
        (2, 'tt0389790'),
        (1, 'tt0257106'),
        (2, 'tt4116284'),
        (1, 'tt0306047'),
        (2, 'tt0362120');



