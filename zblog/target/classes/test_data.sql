insert into role VALUES (1,'ROLE_ADMIN');

insert into users (id, email, enabled, password, username) values (1,'simiyu@gmail.com','true','$2a$11$LhUT8JM5YZHKrgVonqnMrOcQpR3Dz0Ll05RIXUWfP3MNuAwTizuxi','javenock');

insert into users_roles (users_id, role_id) values (1, 1);
