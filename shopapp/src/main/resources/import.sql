-- ==========================================
-- INSERTAR USUARIOS
-- ==========================================
-- Insertar usuario Admin
INSERT INTO `users` (`username`, `email`, `first_name`, `last_name`, `password`, `verified`, `fecha_alta`) VALUES ('admin', 'admin@gmail.com', 'admin', 'admin', '$2a$10$.2Dshfues.xDTH.Rey7Zr.OJEemh8A6smHfM1KWaYy88iUqnQGDDi', 1, NOW());

-- Insertar usuario User
INSERT INTO `users` (`username`, `email`, `first_name`, `last_name`, `password`, `verified`, `fecha_alta`) VALUES ('user', 'user@gmail.com', 'user', 'user', '$2a$10$ZHIJob9WN.Ox8ip/5VxYw.9FBCCMlfpKIh.GrtnuAcBcNMpUGI6/m', 1, NOW());


-- ==========================================
-- ROLES Y ASIGNACIÓN
-- ==========================================
INSERT INTO `db_shopapp`.`roles` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `db_shopapp`.`roles` (`name`) VALUES ('ROLE_USER');

INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES (1, 1);
INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES (2, 1);
INSERT INTO `db_shopapp`.`users_roles` (`role_id`, `user_id`) VALUES (2, 2);

-- Inserción de categorías existentes
INSERT INTO category (id, image) VALUES (1, 'Clothes.png'); -- Categoría 1: Ropa
INSERT INTO category (id, image) VALUES (2, 'Accesories.png'); -- Categoría 2: Accesorios
INSERT INTO category (id, image) VALUES (3, 'Footwear.png'); -- Categoría 3: Calzado
INSERT INTO category (id, image) VALUES (4, 'Home.png'); -- Categoría 4: Hogar
INSERT INTO category (id, image) VALUES (5, 'Pets.png'); -- Categoría 5: Mascotas

-- Traducciones para la Categoría 1: Ropa
INSERT INTO category_translation (language_code, name, category_id) VALUES ('es', 'Ropa', 1);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('en', 'Clothing', 1);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('ca', 'Roba', 1);

-- Traducciones para la Categoría 2: Accesorios
INSERT INTO category_translation (language_code, name, category_id) VALUES ('es', 'Accesorios', 2);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('en', 'Accessories', 2);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('ca', 'Accessoris', 2);

-- Traducciones para la Categoría 3: Calzado
INSERT INTO category_translation (language_code, name, category_id) VALUES ('es', 'Calzado', 3);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('en', 'Footwear', 3);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('ca', 'Calçat', 3);

-- Traducciones para la Categoría 4: Hogar
INSERT INTO category_translation (language_code, name, category_id) VALUES ('es', 'Hogar', 4);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('en', 'Home', 4);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('ca', 'Llar', 4);

-- Traducciones para la Categoría 5: Mascotas
INSERT INTO category_translation (language_code, name, category_id) VALUES ('es', 'Mascotas', 5);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('en', 'Pets', 5);
INSERT INTO category_translation (language_code, name, category_id) VALUES ('ca', 'Mascotes', 5);


-- ==========================================
-- CATEGORÍAS SOSTENIBLES
-- ==========================================
INSERT INTO sustainable_category (id) VALUES (1); -- Categoría 1: Vegano
INSERT INTO sustainable_category (id) VALUES (2); -- Categoría 2: Handmade
INSERT INTO sustainable_category (id) VALUES (3); -- Categoría 3: Reciclado
INSERT INTO sustainable_category (id) VALUES (4); -- Categoría 4: Orgánico
INSERT INTO sustainable_category (id) VALUES (5); -- Categoría 5: Zero Waste

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

-- Traducciones para la Categoría Sostenible 4: Orgánico
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('es', 'Orgánico', 4);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('en', 'Organic', 4);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('ca', 'Orgànic', 4);

-- Traducciones para la Categoría Sostenible 5: Zero Waste
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('es', 'Zero Waste', 5);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('en', 'Zero Waste', 5);
INSERT INTO sustainable_category_translation (language_code, name, sustainable_category_id) VALUES ('ca', 'Zero Waste', 5);

-- ==========================================
-- CREAR COMPAÑÍA ASOCIADA AL USUARIO
-- ==========================================
INSERT INTO company (id, name, description, address, phone_number, email, administrator_id, version) VALUES (1, 'EcoSustainable Co.', 'Empresa dedicada a la fabricación y venta de productos sostenibles y veganos.', 'Calle Verde 123, Barcelona', '600123456', 'info@ecosustainable.com', 2, 0);


INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (1, 21.00, 1, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Camiseta Vegana', 'Camiseta fabricada con algodón orgánico.', 1), ('en', 'Vegan T-Shirt', 'T-shirt made of organic cotton.', 1), ('ca', 'Samarreta Vegana', 'Samarreta feta amb cotó orgànic.', 1);
INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (1, 'Talla', 1);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('S', 19.99, 15.00, 50, 1), ('M', 21.99, 16.00, 50, 1), ('L', 23.99, 17.00, 50, 1), ('XL', 25.99, 18.00, 50, 1);
INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (1, 1);
INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (1, 'vegan_tshirt_1.jpg'), (1, 'vegan_tshirt_2.jpg'), (1, 'vegan_tshirt_3.jpg');


INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (2, 21.00, 2, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Bolso Reciclado', 'Bolso hecho de materiales reciclados.', 2), ('en', 'Recycled Bag', 'Bag made from recycled materials.', 2), ('ca', 'Bossa Reciclada', 'Bossa feta amb materials reciclats.', 2);
INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (2, 'singlePrice', 2);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('unique', 29.99, 20.00, 100, 2);
INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (2, 3);
INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (2, 'recycled_bag_1.jpg'), (2, 'recycled_bag_2.jpg'), (2, 'recycled_bag_3.jpg');


INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (3, 21.00, 1, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Sudadera Hecha a Mano', 'Sudadera sostenible y fabricada a mano.', 3), ('en', 'Handmade Sweater', 'Sustainable and handmade sweater.', 3), ('ca', 'Dessuadora Fet a Mà', 'Dessuadora sostenible feta a mà.', 3);
INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (3, 'Talla', 3);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('S', 29.99, 20.00, 50, 3), ('M', 31.99, 22.00, 50, 3), ('L', 34.99, 24.00, 50, 3);
INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (3, 2);
INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (3, 'handmade_sweater_1.jpg'), (3, 'handmade_sweater_2.jpg'), (3, 'handmade_sweater_3.jpg');


INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (4, 21.00, 3, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Zapatillas Veganas', 'Zapatillas fabricadas sin productos de origen animal.', 4), ('en', 'Vegan Shoes', 'Shoes made without animal products.', 4), ('ca', 'Sabates Veganes', 'Sabates fabricades sense productes animals.', 4);
INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (4, 'Talla', 4);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('38', 49.99, 35.00, 50, 4), ('39', 49.99, 35.00, 50, 4), ('40', 49.99, 35.00, 50, 4), ('41', 49.99, 35.00, 50, 4);
INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (4, 1);
INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (4, 'vegan_shoes_1.jpg'), (4, 'vegan_shoes_2.jpg'), (4, 'vegan_shoes_3.jpg');


INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (5, 21.00, 2, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Mochila Reciclada', 'Mochila fabricada con materiales reciclados.', 5), ('en', 'Recycled Backpack', 'Backpack made from recycled materials.', 5), ('ca', 'Motxilla Reciclada', 'Motxilla feta amb materials reciclats.', 5);
INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (5, 'singlePrice', 5);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('unique', 39.99, 25.00, 50, 5);
INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (5, 3);
INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (5, 'recycled_backpack_1.jpg'), (5, 'recycled_backpack_2.jpg'), (5, 'recycled_backpack_3.jpg');
