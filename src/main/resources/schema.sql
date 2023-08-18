DROP TABLE IF EXISTS film_genre;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS users_likes_films;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS mpa;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS PUBLIC.mpa (
	mpa_id INTEGER NOT NULL AUTO_INCREMENT,
	name CHARACTER VARYING(64) NOT NULL,
	CONSTRAINT mpa_pk PRIMARY KEY (mpa_id)
);
CREATE UNIQUE INDEX IF NOT EXISTS pk_mpa_index ON PUBLIC.mpa (mpa_id);

CREATE TABLE IF NOT EXISTS PUBLIC.films (
	film_id INTEGER NOT NULL AUTO_INCREMENT,
	name CHARACTER VARYING(255) NOT NULL,
	description CHARACTER VARYING(255) NOT NULL,
	release_date DATE NOT NULL,
	duration INTEGER NOT NULL,
	mpa_id INTEGER DEFAULT 1,
	CONSTRAINT films_pk PRIMARY KEY (film_id),
	CONSTRAINT films_mpa_FK FOREIGN KEY (mpa_id) REFERENCES PUBLIC.mpa(mpa_id)
);
CREATE UNIQUE INDEX IF NOT EXISTS pk_films_index ON PUBLIC.films(film_id);

CREATE TABLE IF NOT EXISTS PUBLIC.genre (
	genre_id INTEGER NOT NULL AUTO_INCREMENT,
	name CHARACTER VARYING(64) NOT NULL,
	CONSTRAINT genre_pk PRIMARY KEY (genre_id)
);
CREATE UNIQUE INDEX IF NOT EXISTS pk_genre_index ON PUBLIC.genre(genre_id);

CREATE TABLE IF NOT EXISTS PUBLIC.film_genre (
	film_id INTEGER NOT NULL,
	genre_id INTEGER NOT NULL,
	CONSTRAINT film_genre_pk PRIMARY KEY (film_id, genre_id),
	CONSTRAINT film_genre_film_fk FOREIGN KEY (film_id) REFERENCES PUBLIC.films(film_id),
	CONSTRAINT film_genre_genre_fk FOREIGN KEY (genre_id) REFERENCES PUBLIC.genre(genre_id)
);
CREATE UNIQUE INDEX IF NOT EXISTS pk_film_genre_index ON PUBLIC.film_genre(film_id,genre_id);

CREATE TABLE IF NOT EXISTS PUBLIC.users(
	user_id INTEGER NOT NULL AUTO_INCREMENT,
	login CHARACTER VARYING(255) NOT NULL,
	name CHARACTER VARYING(255) NOT NULL,
	email CHARACTER VARYING(255) NOT NULL,
	birthday DATE,
	CONSTRAINT users_pk PRIMARY KEY (user_id)
);
CREATE UNIQUE INDEX IF NOT EXISTS pk_users_index ON PUBLIC.users (user_id);

CREATE TABLE IF NOT EXISTS PUBLIC.users_likes_films (
	user_id INTEGER NOT NULL,
	film_id INTEGER NOT NULL,
	CONSTRAINT users_likes_films_pk PRIMARY KEY (user_id,film_id),
	CONSTRAINT users_likes_films_user_fk FOREIGN KEY (user_id) REFERENCES PUBLIC.users(user_id),
	CONSTRAINT users_likes_films_film_fk FOREIGN KEY (film_id) REFERENCES PUBLIC.films(film_id)

);
CREATE UNIQUE INDEX IF NOT EXISTS pk_users_likes_films_index ON PUBLIC.users_likes_films (user_id,film_id);

CREATE TABLE IF NOT EXISTS PUBLIC.friends (
	first_id INTEGER NOT NULL,
	second_id INTEGER NOT NULL,
	CONSTRAINT friends_pk PRIMARY KEY (first_id, second_id),
	CONSTRAINT friends_first_id_FK FOREIGN KEY (first_id) REFERENCES PUBLIC.users(user_id),
	CONSTRAINT friends_second_id_FK FOREIGN KEY (second_id) REFERENCES PUBLIC.users(user_id)
);
CREATE INDEX IF NOT EXISTS pk_friends_index ON PUBLIC.friends (first_id, second_id);




