CREATE TYPE profile_tp AS ENUM ('ADMIN', 'DEFAULT');

CREATE TABLE "user" (
	id INT PRIMARY KEY,
	name VARCHAR(256) NOT NULL,
	email VARCHAR(256) NOT NULL,
	cpf VARCHAR(11) NOT NULL,
	phone VARCHAR(12) null null,
	area_id INT NOT NULL,
	"profile" profile_tp NOT NULL
);

CREATE TABLE area(
	id INT PRIMARY KEY,
	name VARCHAR(256) NOT NULL
);

CREATE TABLE ticket(
	id INT PRIMARY KEY,
	title VARCHAR(256) NOT NULL,
	description VARCHAR(512),
	curatorship_message VARCHAR(512)
	created_date TIMESTAMP NOT NULL,
	area_id INT NOT NULL,
	status_id INT NOT NULL,
	author_id INT NOT NULL,
	curator_id INT NOT NULL
);

CREATE TABLE status(
	id INT PRIMARY KEY,
	name VARCHAR(256) NOT NULL,
	created_date TIMESTAMP NOT NULL
);