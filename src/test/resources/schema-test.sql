CREATE TABLE freelancers
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(50)  NOT NULL,
    email            VARCHAR(255) NOT NULL,
    phone            VARCHAR(20),
    city             VARCHAR(50),
    evaluation_score INT,
    freelancer_type  VARCHAR(20)  NOT NULL,
    portfolio_url    VARCHAR(255) -- designer only
);

CREATE TABLE design_tools
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE freelancers_design_tools
(
    freelancer_id  BIGINT NOT NULL,
    design_tool_id BIGINT NOT NULL,
    PRIMARY KEY (freelancer_id, design_tool_id),
    FOREIGN KEY (freelancer_id) REFERENCES freelancers (id),
    FOREIGN KEY (design_tool_id) REFERENCES design_tools (id)
);

CREATE TABLE languages
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE freelancers_languages
(
    freelancer_id BIGINT NOT NULL,
    language_id   BIGINT NOT NULL,
    PRIMARY KEY (freelancer_id, language_id),
    FOREIGN KEY (freelancer_id) REFERENCES freelancers (id),
    FOREIGN KEY (language_id) REFERENCES languages (id)
);

CREATE TABLE specialties
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE freelancers_specialties
(
    freelancer_id BIGINT NOT NULL,
    specialty_id  BIGINT NOT NULL,
    PRIMARY KEY (freelancer_id, specialty_id),
    FOREIGN KEY (freelancer_id) REFERENCES freelancers (id),
    FOREIGN KEY (specialty_id) REFERENCES specialties (id)
);

CREATE TABLE jobs
(
    id            BIGSERIAL PRIMARY KEY,
    freelancer_id BIGINT    NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    job_status    VARCHAR(30)        DEFAULT 'AVAILABLE',
    description   TEXT,
    FOREIGN KEY (freelancer_id) REFERENCES freelancers (id) ON DELETE CASCADE
);

CREATE TABLE comments
(
    id             BIGSERIAL PRIMARY KEY,
    job_id         BIGINT    NOT NULL,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    commenter_name VARCHAR(30),
    content        TEXT,
    FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE
);

-- freelancer 1
INSERT INTO freelancers (name,
                         email,
                         phone,
                         city,
                         evaluation_score,
                         freelancer_type,
                         portfolio_url)
VALUES ('John Doe',
        'johndoe@example.com',
        '+1234567890',
        'New York',
        0,
        'DESIGNER',
        'https://johndoeportfolio.com');

INSERT INTO design_tools (name)
VALUES ('Figma'),
       ('Adobe');

INSERT INTO freelancers_design_tools (freelancer_id, design_tool_id)
VALUES (1, 1),
       (1, 2);

-- freelancer 2
INSERT INTO freelancers (name, email, phone, city, evaluation_score, freelancer_type, portfolio_url)
VALUES ('Jane Smith', 'janesmith@example.com', '+0987654321', 'San Francisco', 0, 'SOFTWARE_DEVELOPER', NULL);

INSERT INTO languages (name)
VALUES ('Java'),
       ('Go'),
       ('Python');

INSERT INTO freelancers_languages (freelancer_id, language_id)
VALUES (2, 1),
       (2, 2),
       (2, 3);

INSERT INTO specialties (name)
VALUES ('Frontend'),
       ('Backend');

INSERT INTO freelancers_specialties (freelancer_id, specialty_id)
VALUES (2, 1),
       (2, 2);

/** Jobs **/
INSERT INTO jobs (freelancer_id, created_at, description)
VALUES (1, '2025-01-08T19:05:21', 'Create website');

INSERT INTO jobs (freelancer_id, created_at, description)
VALUES (2, '2025-01-08T19:05:21', 'Create backend');

INSERT INTO jobs (freelancer_id, job_status, created_at, description)
VALUES (2, 'FINISHED', '2025-01-08T19:05:21', 'Create frontend');

/** comments **/
INSERT INTO comments (job_id, commenter_name, content)
VALUES (1, 'commenter', 'nice job');

INSERT INTO comments (job_id, commenter_name, content)
VALUES (1, 'commenter2', 'nice job 2');


