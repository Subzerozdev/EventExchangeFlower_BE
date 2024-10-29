create database eventflowerexchange;
use eventflowerexchange;
create table `roles`
(
	id INT NOT NULL PRIMARY KEY,
    name NVARCHAR(50) NOT  NULL
);

insert into `roles`(id,name) value(0,"Admin");
insert into `roles`(id,name) value(1,"Seller");
insert into `roles`(id,name) value(2,"Customer");

create table `users`
(
	id CHAR(36) NOT NULL PRIMARY KEY,
	balance float,
    email nvarchar(100) NOT  NULL unique,
    `password` nvarchar(100) NOT  NULL,
    full_name nvarchar(100)  NOT  NULL,
    phone nvarchar(10) unique,
    address nvarchar(250),
	created_at DATETIME  default current_timestamp,
    updated_at DATETIME   default current_timestamp,
    is_active bit default 0,
    role_id INT default 3,
     FOREIGN KEY (role_id) REFERENCES `roles`(id)
);

create table `shop`
(
	id char(36) PRIMARY KEY,
    `description` text,
    shop_address varchar(255),
    shop_name varchar(150),
    user_id CHAR(36) NOT NULL,
    FOREIGN KEY  (user_id) REFERENCES `users`(id)
);

create table `eventcategories`
(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name nvarchar(100)
);

create table `types`
(
	id INT AUTO_INCREMENT NOT  NULL PRIMARY KEY,
    name NVARCHAR(100)
);

create table `posts`
(
	id INT AUTO_INCREMENT NOT  NULL PRIMARY KEY,
	name VARCHAR(350) COMMENT 'Tên sản phẩm',
    `description` LONGTEXT ,
    thumbnail VARCHAR(300) ,
    address NVARCHAR(250) ,
    start_date DATETIME,
    end_date DATETIME,    
	price FLOAT NOT NULL CHECK(price >= 0),  
    user_id CHAR(36) NOT NULL,
    `status` INT DEFAULT 0,
    category_id INT,
    FOREIGN KEY  (user_id) REFERENCES `users`(id),
    FOREIGN KEY (category_id) REFERENCES `eventcategories`(id)
);

CREATE TABLE post_images (
    id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT, -- CHỈ RA BỨC ẢNH NÀY SẼ THUỘC SẢN PHẨM NÀO
     CONSTRAINT fk_post_images_post_id
     FOREIGN KEY (post_id) REFERENCES  posts(id) ON DELETE CASCADE, -- posts xóa thì image sẽ bị xóa theo
     image_url VARCHAR(300)
);


create table `orders`
(
	id INT NOT NULL auto_increment PRIMARY KEY,
	full_name VARCHAR(100) NOT NULL DEFAULT '',
	phone_number VARCHAR(20) NOT NULL,
	email VARCHAR(100) NOT NULL DEFAULT '',
	address VARCHAR(200) NOT NULL,
	note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	total_money FLOAT CHECK (total_money >=0),
    `status` VARCHAR(50) ,
	user_id CHAR(36),
   FOREIGN KEY (user_id) REFERENCES `users`(id)
);

create table `order_details`
(
	id INT  auto_increment not null,
    total_money float check (total_money >=0),
    order_id INT NOT NULL,
    post_id INT NOT NULL,
    primary key (id, order_id, post_id),
    foreign key (order_id) references `orders`(id),
    foreign key (post_id) references `posts`(id)
);

create table `typeandpost`
(
	id int auto_increment not null,
    type_id INT NOT NULL,
    post_id INT NOT NULL,
    primary key (id, type_id, post_id),
    foreign key (type_id) references `types`(id),
    foreign key (post_id) references `posts`(id)
);

create table OTPEmail
(
id int auto_increment not null primary key,
OTP int not null,
expiry_date datetime,
user_id CHAR(36) ,
foreign key (user_id) references `users`(id)
);

-- CREATE TABLE tokens (
--     id int PRIMARY KEY AUTO_INCREMENT,
--     token varchar(255) UNIQUE NOT NULL,
--     token_type varchar(50) NOT NULL,
--     expiration_date DATETIME,
--     revoked tinyint(1) NOT NULL,
--     expired tinyint(1) NOT NULL,
--     user_id CHAR(36), 
--     FOREIGN KEY (user_id) REFERENCES users(id)
-- );

CREATE TABLE payment(
	id INT PRIMARY KEY AUTO_INCREMENT,
	create_at datetime,
	`payment_method` INT DEFAULT 0,
    total FLOAT CHECK (total >=0),
	order_id INT,
	FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE transactions (
	id int AUTO_INCREMENT PRIMARY KEY ,
    create_at datetime,
    amount FLOAT CHECK (amount >=0),
    from_id CHAR(36),
    FOREIGN KEY (from_id) REFERENCES users(id),
    to_id CHAR(36),
    FOREIGN KEY (to_id) REFERENCES users(id),
    payment_id INT,
    FOREIGN KEY (payment_id) REFERENCES payment(id),
	`status` INT DEFAULT 0,
	`description` VARCHAR(50)
);

CREATE TABLE feedback (
	id int AUTO_INCREMENT PRIMARY KEY ,
    content nvarchar(200),
    rating int not null,
	customer_id CHAR(36),
    FOREIGN KEY (customer_id) REFERENCES users(id),    
	shop_id CHAR(36),
    FOREIGN KEY (shop_id) REFERENCES shop(id)
)