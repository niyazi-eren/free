CREATE TABLE jobs
(
    id            BIGSERIAL PRIMARY KEY,
    freelancer_id BIGINT    NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    job_status    VARCHAR(30)        DEFAULT 'AVAILABLE',
    description  TEXT,
    FOREIGN KEY (freelancer_id) REFERENCES freelancers (id) ON DELETE CASCADE
);