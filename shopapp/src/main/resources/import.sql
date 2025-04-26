
INSERT INTO `db_shopapp`.`users` (`username`, `email`, `first_name`, `last_name`, `password`) VALUES ('user', 'user@gmail.com', 'user', 'user', '$2a$10$ZHIJob9WN.Ox8ip/5VxYw.9FBCCMlfpKIh.GrtnuAcBcNMpUGI6/m');
INSERT INTO `db_shopapp`.`users` (`username`, `email`, `first_name`, `last_name`, `password`) VALUES ('admin', 'admin@gmail.com', 'admin', 'admin', '$2a$10$.2Dshfues.xDTH.Rey7Zr.OJEemh8A6smHfM1KWaYy88iUqnQGDDi');


INSERT INTO `db_shopapp`.`roles` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `db_shopapp`.`roles` (`name`) VALUES ('ROLE_USER');

INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES ('2', '1');

INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES ('2', '2');