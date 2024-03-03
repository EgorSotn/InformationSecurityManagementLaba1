DROP TABLE IF EXISTS public.user_settings CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;
DROP TABLE IF EXISTS public.roles CASCADE;
DROP TABLE IF EXISTS public.user_role CASCADE;
DROP TABLE IF EXISTS public.encoding_key CASCADE;

CREATE SEQUENCE IF NOT EXISTS user_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS user_settings_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS roles_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS encoding_key_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS public.user_settings
(
    id                  BIGINT NOT NULL,
    block               BOOLEAN,
    match_password       VARCHAR(255),
    restriction_password BOOLEAN,
    CONSTRAINT pk_user_settings PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.users
(
    id            BIGINT       NOT NULL,
    username      VARCHAR(50)  NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    user_settings_id BIGINT             ,
    CONSTRAINT pk_users PRIMARY KEY (id),
    FOREIGN KEY (user_settings_id) REFERENCES user_settings (id)
);
CREATE TABLE IF NOT EXISTS public.roles
(
    id            BIGINT NOT NULL,
    name          VARCHAR(50) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.user_role
(
    id_role bigint NOT NULL,
    id_user bigint NOT NULL,
    FOREIGN KEY (id_role) REFERENCES roles (id),
    FOREIGN KEY (id_user) REFERENCES users (id)
);
CREATE TABLE IF NOT EXISTS public.encoding_key
(
    id            BIGINT NOT NULL,
    key          VARCHAR(255) NOT NULL,
    CONSTRAINT pk_encoding_key PRIMARY KEY (id)
);