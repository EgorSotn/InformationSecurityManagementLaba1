INSERT INTO public.users (id,email, username, password) VALUES (1,'admin@gmail.com', 'admin', '$2a$10$qBVtiAc8YPhMfpnZ8hP26eJECDTzpD8l9B1ANHkX0scmJjVgtmZS2');
INSERT INTO public.users (id,email, username, password) VALUES (2,'user@gmail.com', 'user', '$2a$10$qBVtiAc8YPhMfpnZ8hP26eJECDTzpD8l9B1ANHkX0scmJjVgtmZS2');
INSERT INTO public.roles (id,name) VALUES (1,'ROLE_ADMIN');
INSERT INTO public.roles (id,name) VALUES (2,'ROLE_USER');
INSERT INTO public.user_role (id_role, id_user) VALUES (1, 1);
INSERT INTO public.user_role (id_role, id_user) VALUES (2, 2);