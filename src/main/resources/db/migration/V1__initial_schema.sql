CREATE TABLE freelancers (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(50) NOT NULL,
                             email VARCHAR(255) NOT NULL,
                             phone VARCHAR(20),
                             city VARCHAR(50),
                             evaluation_score INT,
                             freelancer_type VARCHAR(20) NOT NULL,
                             portfolio_url VARCHAR(255)          -- designer only
);

CREATE TABLE design_tools (
                              id BIGSERIAL PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              UNIQUE (name)
);

CREATE TABLE freelancers_design_tools (
    freelancer_id BIGINT NOT NULL,
    design_tool_id BIGINT NOT NULL,
    PRIMARY KEY (freelancer_id, design_tool_id),
    FOREIGN KEY (freelancer_id) REFERENCES freelancers (id),
    FOREIGN KEY (design_tool_id) REFERENCES design_tools (id)
);

CREATE TABLE languages (
                              id BIGSERIAL PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              UNIQUE (name)
);

CREATE TABLE freelancers_languages (
                                          freelancer_id BIGINT NOT NULL,
                                          language_id BIGINT NOT NULL,
                                          PRIMARY KEY (freelancer_id, language_id),
                                          FOREIGN KEY (freelancer_id) REFERENCES freelancers (id),
                                          FOREIGN KEY (language_id) REFERENCES languages (id)
);

CREATE TABLE specialties (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           UNIQUE (name)
);

CREATE TABLE freelancers_specialties (
                                       freelancer_id BIGINT NOT NULL,
                                       specialty_id BIGINT NOT NULL,
                                       PRIMARY KEY (freelancer_id, specialty_id),
                                       FOREIGN KEY (freelancer_id) REFERENCES freelancers (id),
                                       FOREIGN KEY (specialty_id) REFERENCES specialties (id)
);