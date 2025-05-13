
INSERT INTO `db_shopapp`.`users` (`username`, `email`, `first_name`, `last_name`, `password`) VALUES ('user', 'user@gmail.com', 'user', 'user', '$2a$10$ZHIJob9WN.Ox8ip/5VxYw.9FBCCMlfpKIh.GrtnuAcBcNMpUGI6/m');
INSERT INTO `db_shopapp`.`users` (`username`, `email`, `first_name`, `last_name`, `password`) VALUES ('admin', 'admin@gmail.com', 'admin', 'admin', '$2a$10$.2Dshfues.xDTH.Rey7Zr.OJEemh8A6smHfM1KWaYy88iUqnQGDDi');


INSERT INTO `db_shopapp`.`roles` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `db_shopapp`.`roles` (`name`) VALUES ('ROLE_USER');

INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES ('2', '1');

INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES ('2', '2');

INSERT INTO category (id) VALUES (1); -- Categoría 1
INSERT INTO category (id) VALUES (2); -- Categoría 2

-- Traducciones para la Categoría 1
INSERT INTO category_translation (language_code, name, category_id) VALUES ('es', 'Ropa', 1);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('en', 'Clothing', 1);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('ca', 'Roba', 1);

-- Traducciones para la Categoría 2
INSERT INTO category_translation (language_code, name, category_id) VALUES ('es', 'Accesorios', 2);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('en', 'Accessories', 2);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('ca', 'Accessoris', 2);

-- Insertar categorías base
INSERT INTO sustainable_category (id) VALUES (1); -- Categoría 1: Vegano
INSERT INTO sustainable_category (id) VALUES (2); -- Categoría 2: Handmade
INSERT INTO sustainable_category (id) VALUES (3); -- Categoría 3: Reciclado

-- Traducciones para la Categoría Sostenible 1: Vegano
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('es', 'Vegano', 1);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('en', 'Vegan', 1);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('ca', 'Vegà', 1);

-- Traducciones para la Categoría Sostenible 2: Handmade
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('es', 'Hecho a mano', 2);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('en', 'Handmade', 2);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('ca', 'Fet a mà', 2);

-- Traducciones para la Categoría Sostenible 3: Reciclado
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('es', 'Reciclado', 3);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('en', 'Recycled', 3);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('ca', 'Reciclat', 3);