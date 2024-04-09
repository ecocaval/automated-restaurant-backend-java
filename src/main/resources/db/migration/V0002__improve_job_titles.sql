ALTER TABLE job_title DROP COLUMN permission;

ALTER TABLE collaborator ADD IF NOT EXISTS is_owner BOOLEAN NOT NULL;

CREATE TABLE job_title_job_tile_permissions (
   job_title_id UUID NOT NULL,
   job_title_permission_id UUID NOT NULL
);

CREATE TABLE job_title_permission (
   id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   name VARCHAR(255) NOT NULL,
   restaurant_id UUID NOT NULL,
   CONSTRAINT pk_jobtitlepermission PRIMARY KEY (id)
);

ALTER TABLE job_title_permission ADD CONSTRAINT FK_JOBTITLEPERMISSION_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE job_title_job_tile_permissions ADD CONSTRAINT fk_jobtitjobtilper_on_job_title FOREIGN KEY (job_title_id) REFERENCES job_title (id);

ALTER TABLE job_title_job_tile_permissions ADD CONSTRAINT fk_jobtitjobtilper_on_job_title_permission FOREIGN KEY (job_title_permission_id) REFERENCES job_title_permission (id);
