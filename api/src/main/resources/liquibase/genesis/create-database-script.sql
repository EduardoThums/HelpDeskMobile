CREATE TYPE profile_tp AS ENUM ('ADMIN', 'DEFAULT');
CREATE TYPE status_st AS ENUM ('PENDING', 'APPROVED', 'DENIED');

CREATE TABLE area (
	id INT PRIMARY KEY,
	name VARCHAR(256) NOT NULL
);

CREATE TABLE "user" (
	id INT,
	name VARCHAR(256) NOT NULL,
	email VARCHAR(256) NOT NULL UNIQUE,
	cpf VARCHAR(11) NOT NULL UNIQUE,
	phone VARCHAR(12) NOT NULL UNIQUE,
	area_id INT NOT NULL,
	"profile" profile_tp NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (area_id) REFERENCES area (id)
);

CREATE INDEX area_id_user_index ON "user" (area_id);

CREATE TABLE ticket (
	id INT,
	title VARCHAR(256) NOT NULL,
	description VARCHAR(512),
	curatorship_message VARCHAR(512),
	created_date TIMESTAMP NOT NULL,
	area_id INT NOT NULL,
	author_id INT NOT NULL,
	curator_id INT NOT null,
    PRIMARY KEY (id),
    FOREIGN KEY (area_id) REFERENCES area (id),
    FOREIGN KEY (author_id) REFERENCES "user" (id),
    FOREIGN KEY (curator_id) REFERENCES "user" (id)
);

CREATE INDEX area_id_ticket_index ON ticket (area_id);
CREATE INDEX author_id_ticket_index ON ticket (author_id);
CREATE INDEX curator_id_ticket_index ON ticket (curator_id);

CREATE TABLE ticket_status (
	id INT,
	ticket_id INT NOT NULL,
    current_status BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL,
    status status_st NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);

CREATE INDEX ticket_id_ticket_status_index ON ticket_status (ticket_id);