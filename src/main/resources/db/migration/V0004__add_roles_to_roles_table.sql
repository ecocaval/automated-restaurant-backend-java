CREATE TABLE role (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   name VARCHAR(50) NOT NULL,
   CONSTRAINT pk_role PRIMARY KEY (id)
);

INSERT INTO role (id, creation_date, name) values (gen_random_uuid(), NOW(), 'USER');
INSERT INTO role (id, creation_date, name) values (gen_random_uuid(), NOW(), 'ADMIN');

CREATE TABLE collaborator_roles (
  collaborator_id UUID NOT NULL,
  role_id UUID NOT NULL
);

ALTER TABLE collaborator_roles ADD CONSTRAINT fk_colrol_on_collaborator FOREIGN KEY (collaborator_id) REFERENCES collaborator (id);

ALTER TABLE collaborator_roles ADD CONSTRAINT fk_colrol_on_role FOREIGN KEY (role_id) REFERENCES role (id);

CREATE TABLE refresh_token (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   account_id UUID NOT NULL,
   CONSTRAINT pk_refreshtoken PRIMARY KEY (id)
);

ALTER TABLE refresh_token ADD CONSTRAINT FK_REFRESHTOKEN_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES collaborator (id);