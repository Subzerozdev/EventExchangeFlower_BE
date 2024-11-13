use eventflowerexchange;

-- Role
insert into `roles`(id,name) value(0,"Admin");
insert into `roles`(id,name) value(1,"Seller");
insert into `roles`(id,name) value(2,"Customer");

-- Fee
insert into `fee`(id,`type`,amount) value(1,"Flatform Fee",0.0);

-- User
insert into `users`(id, balance,email,`password`,full_name,phone,address,created_at,updated_at,is_active,role_id) 
value("3b86edc5-98b5-11ef-8472-2c6dc10e488b",0,"user01@gmail.com","$2a$12$EQg.ZMSgAEBMiLHhVh.53e8D4/.Tay.HSyC0SPccFmO5tiu/SFrOi","User 01","0909987675","789 Phan Đăng Lưu, Phú Nhuận, TP.HCM", "2024-11-02 07:43:19", null,true,2);

insert into `users`(id,balance,email,`password`,full_name,phone,address,created_at,updated_at,is_active,role_id) 
value("5ab23788-98b5-11ef-8472-2c6dc10e488b",0,"user02@gmail.com","$2a$12$sDz2DImPhdruzlwLAPahE.rOa7B9DIt4SZ8ltA47uANhpB6Yo6fWO","User 02","0909987671","890 Điện Biên Phủ, Bình Thạnh, TP.HCM", "2024-11-02 07:43:19", null,true,2);

insert into `users`(id,balance,email,`password`,full_name,phone,address,created_at,updated_at,is_active,role_id) 
value("5ab4992a-98b5-11ef-8472-2c6dc10e488b",0,"seller01@gmail.com","$2a$12$Su72PtpQFrOKEcFqq1.5VeK5CwejEpRNHrmHL64j8r3MbFqUSEsxm","Seller 01","0909987672","789 Phan Đăng Lưu, Phú Nhuận, TP.HCM", "2024-11-02 07:43:19", null,true,1);

insert into `users`(id,balance,email,`password`,full_name,phone,address,created_at,updated_at,is_active,role_id) 
value("5ab548eb-98b5-11ef-8472-2c6dc10e488b",0,"seller02@gmail.com","$2a$12$POP2vZ4faMkKMrkFHeUij.yhY4Z0aePuAUbrKNg69FcUR3xCqT1bW","Seller 02","0909987673","456 Nguyễn Thị Minh Khai, Quận 3, TP.HCM", "2024-11-02 07:43:19", null,true,1);

-- Shop
insert into `shop`(`description`,shop_address,`shop_name`,user_id) 
value("Shop bán các bộ hoa sự kiện phục vụ khách hàng","234 Cách Mạng Tháng 8, Quận 10, TP.HCM","Shop Hoa Xinh","5ab4992a-98b5-11ef-8472-2c6dc10e488b");

insert into `shop`(`description`,shop_address,`shop_name`,user_id) 
value("Shop bán các bộ hoa sự kiện phục vụ khách hàng","234 Cách Mạng Tháng 8, Quận 10, TP.HCM","Shop An Bình","5ab548eb-98b5-11ef-8472-2c6dc10e488b");

-- eventcategories
insert into `eventcategories`(id,`name`) value(1,"Sự kiện đám cưới");
insert into `eventcategories`(id,`name`) value(2,"Sự kiện thôi nôi");
insert into `eventcategories`(id,`name`) value(3,"Sự kiện tân gia");
insert into `eventcategories`(id,`name`) value (4,"Sự kiện khánh thành");
insert into `eventcategories`(id,`name`) value(5,"Sự kiện khai trương");

-- types
insert into `types`(id,`name`) value(1,"Hoa Hồng Đỏ");
insert into `types`(id,`name`) value(2,"Hoa Ly Trắng");
insert into `types`(id,`name`) value(3,"Hoa Cát Tường");
insert into `types`(id,`name`) value (4,"Hoa Thủy Tiên");
insert into `types`(id,`name`) value(5,"Hoa Bách Hợp");

-- create table `posts`
-- (
-- 	id BIGINT AUTO_INCREMENT NOT  NULL PRIMARY KEY,
-- 	name VARCHAR(350) COMMENT 'Tên sản phẩm',
--     `description` LONGTEXT ,
--     thumbnail VARCHAR(300) ,
--     address NVARCHAR(250) ,
--     start_date DATETIME,
--     end_date DATETIME,    
-- 	price FLOAT NOT NULL CHECK(price >= 0),  
--     user_id CHAR(36) NOT NULL,
--     `status` INT DEFAULT 0,
--     category_id INT,
--     FOREIGN KEY  (user_id) REFERENCES `users`(id),
--     FOREIGN KEY (category_id) REFERENCES `eventcategories`(id)
-- );

-- CREATE TABLE post_images (
--     id BIGINT PRIMARY KEY AUTO_INCREMENT,
--     post_id BIGINT, -- CHỈ RA BỨC ẢNH NÀY SẼ THUỘC SẢN PHẨM NÀO
--      CONSTRAINT fk_post_images_post_id
--      FOREIGN KEY (post_id) REFERENCES  posts(id) ON DELETE CASCADE, -- posts xóa thì image sẽ bị xóa theo
--      image_url VARCHAR(300)
-- );


-- create table `orders`
-- (
-- 	id BIGINT NOT NULL auto_increment PRIMARY KEY,
-- 	full_name VARCHAR(100) NOT NULL DEFAULT '',
-- 	phone_number VARCHAR(20) NOT NULL,
-- 	email VARCHAR(100) NOT NULL DEFAULT '',
-- 	address VARCHAR(200) NOT NULL,
-- 	note VARCHAR(100) DEFAULT '',
--     order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
-- 	total_money FLOAT CHECK (total_money >=0),
--     `status` VARCHAR(50) ,
-- 	fee_id int,
--    FOREIGN KEY (fee_id) REFERENCES `fee`(id),
-- 	user_id CHAR(36),
--    FOREIGN KEY (user_id) REFERENCES `users`(id)
-- );

-- create table `order_details`
-- (
-- 	id BIGINT auto_increment not null,
--     total_money float check (total_money >=0),
--     order_id BIGINT NOT NULL,
--     post_id BIGINT NOT NULL,
--     primary key (id, order_id, post_id),
--     foreign key (order_id) references `orders`(id),
--     foreign key (post_id) references `posts`(id)
-- );

-- create table `typeandpost`
-- (
-- 	id BIGint auto_increment not null,
--     type_id BIGINT NOT NULL,
--     post_id BIGINT NOT NULL,
--     primary key (id, type_id, post_id),
--     foreign key (type_id) references `types`(id),
--     foreign key (post_id) references `posts`(id)
-- );

-- create table OTPEmail
-- (
-- id bigint auto_increment not null primary key,
-- OTP int not null,
-- expiry_date datetime,
-- user_id CHAR(36) ,
-- foreign key (user_id) references `users`(id)
-- );

-- CREATE TABLE payment(
-- 	id BIGINT PRIMARY KEY AUTO_INCREMENT,
-- 	create_at datetime,
-- 	`payment_method` INT DEFAULT 0,
--     total FLOAT CHECK (total >=0),
-- 	order_id BIGINT,
-- 	FOREIGN KEY (order_id) REFERENCES orders(id)
-- );

-- CREATE TABLE transactions (
-- 	id bigint AUTO_INCREMENT PRIMARY KEY ,
--     create_at datetime,
--     amount FLOAT CHECK (amount >=0),
--     from_id CHAR(36),
--     FOREIGN KEY (from_id) REFERENCES users(id),
--     to_id CHAR(36),
--     FOREIGN KEY (to_id) REFERENCES users(id),
--     payment_id BIGINT,
--     FOREIGN KEY (payment_id) REFERENCES payment(id),
-- 	`status` INT DEFAULT 0,
-- 	`description` VARCHAR(50)
-- );

-- CREATE TABLE feedback (
-- 	id bigint AUTO_INCREMENT PRIMARY KEY ,
--     content nvarchar(200),
--     rating int not null,
-- 	customer_id CHAR(36),
--     FOREIGN KEY (customer_id) REFERENCES users(id),    
-- 	shop_id CHAR(36),
--     FOREIGN KEY (shop_id) REFERENCES shop(id)
-- );

