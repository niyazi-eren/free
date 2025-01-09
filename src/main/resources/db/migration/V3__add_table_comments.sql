CREATE TABLE comments
(
    id             BIGSERIAL PRIMARY KEY,
    job_id         BIGINT    NOT NULL,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    commenter_name VARCHAR(30),
    content           TEXT,
    FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE
);
