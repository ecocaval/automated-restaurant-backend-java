DROP TABLE job_title_job_tile_permissions;

DROP TABLE job_title_permission;

ALTER TABLE collaborator ADD IF NOT EXISTS is_admin BOOLEAN NOT NULL;

ALTER TABLE collaborator ADD IF NOT EXISTS is_owner BOOLEAN NOT NULL;

ALTER TABLE collaborator ADD IF NOT EXISTS cpf VARCHAR(11) NOT NULL;

ALTER TABLE collaborator ADD IF NOT EXISTS email VARCHAR(255) NOT NULL;

ALTER TABLE collaborator ADD IF NOT EXISTS password VARCHAR(255) NOT NULL;