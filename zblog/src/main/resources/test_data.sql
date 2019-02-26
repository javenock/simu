INSERT INTO `role` VALUES (1,'ROLE_ADMIN');




INSERT INTO user(id,email,enabled, password, username) VALUES(1,'simiyu@gmail.com',1,'$2a$11$LhUT8JM5YZHKrgVonqnMrOcQpR3Dz0Ll05RIXUWfP3MNuAwTizuxi','javenock');

INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);
