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
-- ==========================================
-- CATEGORÍAS SOSTENIBLES
-- ==========================================
INSERT INTO sustainable_category (id, image) VALUES (1, 'vegan.png'); -- Categoría 1: Vegano
INSERT INTO sustainable_category (id, image) VALUES (2, 'handmade.png'); -- Categoría 2: Handmade
INSERT INTO sustainable_category (id, image) VALUES (3, 'recycled.png'); -- Categoría 3: Reciclado
INSERT INTO sustainable_category (id, image) VALUES (4, 'organic.png'); -- Categoría 4: Orgánico
INSERT INTO sustainable_category (id, image) VALUES (5, 'reused.png'); -- Categoría 5: Zero Waste

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


-- Camisetas

-- Camiseta 1: Zero Waste
INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (6, 21.00, 1, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Camiseta Zero Waste', 'Camiseta fabricada con materiales sostenibles y reciclados.', 6), ('en', 'Zero Waste T-Shirt', 'T-shirt made with sustainable and recycled materials.', 6), ('ca', 'Samarreta Zero Waste', 'Samarreta feta amb materials sostenibles i reciclats.', 6);

INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (6, 'Talla', 6);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('S', 19.99, 15.00, 50, 6), ('M', 21.99, 16.00, 50, 6), ('L', 23.99, 17.00, 50, 6), ('XL', 25.99, 18.00, 50, 6);

INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (6, 5), (6, 1);

INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (6, 'camiseta1.webp'), (6, 'camiseta2.webp'), (6, 'camiseta3.webp');

-- Camiseta 2: Vegana
INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (7, 21.00, 1, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Camiseta Vegana', 'Camiseta fabricada sin materiales de origen animal.', 7), ('en', 'Vegan T-Shirt', 'T-shirt made without animal-derived materials.', 7), ('ca', 'Samarreta Vegana', 'Samarreta feta sense materials d'origen animal.', 7);

INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (7, 'Talla', 7);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('S', 22.99, 18.00, 50, 7), ('M', 24.99, 20.00, 50, 7), ('L', 26.99, 22.00, 50, 7), ('XL', 28.99, 24.00, 50, 7);

INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (7, 1), (7, 4);

INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (7, 'camiseta1.webp'), (7, 'camiseta2.webp'), (7, 'camiseta3.webp');

-- Camiseta 3: Handmade
INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (8, 21.00, 1, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Camiseta Hecha a Mano', 'Camiseta fabricada a mano con materiales sostenibles.', 8), ('en', 'Handmade T-Shirt', 'Handmade t-shirt with sustainable materials.', 8), ('ca', 'Samarreta Fet a Mà', 'Samarreta feta a mà amb materials sostenibles.', 8);

INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (8, 'Talla', 8);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('S', 24.99, 19.00, 50, 8), ('M', 26.99, 20.50, 50, 8), ('L', 28.99, 22.00, 50, 8), ('XL', 30.99, 23.50, 50, 8);

INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (8, 2), (8, 3);

INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (8, 'camiseta1.webp'), (8, 'camiseta2.webp'), (8, 'camiseta3.webp');

-- Camiseta 4: Orgánica
INSERT INTO `product` (`id`, `tax`, `category_id`, `company_id`, `fecha_alta`) VALUES (9, 21.00, 1, 1, NOW());
INSERT INTO `product_translation` (`language_code`, `name`, `description`, `product_id`) VALUES ('es', 'Camiseta Orgánica', 'Camiseta fabricada con algodón 100% orgánico.', 9), ('en', 'Organic T-Shirt', 'T-shirt made with 100% organic cotton.', 9), ('ca', 'Samarreta Orgànica', 'Samarreta feta amb cotó 100% orgànic.', 9);

INSERT INTO `pricing` (`id`, `category_name`, `product_id`) VALUES (9, 'Talla', 9);
INSERT INTO `pricing_value` (`category_value`, `price`, `cost`, `stock`, `pricing_id`) VALUES ('S', 25.99, 19.50, 50, 9), ('M', 27.99, 21.50, 50, 9), ('L', 29.99, 23.50, 50, 9), ('XL', 31.99, 25.50, 50, 9);

INSERT INTO `product_sustainable_categories` (`product_id`, `sustainable_category_id`) VALUES (9, 4), (9, 5);

INSERT INTO `product_photos` (`product_id`, `photos`) VALUES (9, 'camiseta1.webp'), (9, 'camiseta2.webp'), (9, 'camiseta3.webp');

-- Fin de camisetas