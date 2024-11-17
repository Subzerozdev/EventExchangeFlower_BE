create database eventflowerexchange;
use eventflowerexchange;
create table `roles`
(
	id TINYINT NOT NULL PRIMARY KEY,
    name NVARCHAR(50) NOT  NULL
);

CREATE TABLE fee (
	id int AUTO_INCREMENT PRIMARY KEY ,
    `type` nvarchar(50),
    amount float default 0.0
);

create table `users`
(
	id CHAR(36) DEFAULT(UUID()) PRIMARY KEY,
	balance float,
    email nvarchar(150) NOT  NULL unique,
    `password` nvarchar(150) NOT  NULL,
    full_name nvarchar(150)  NOT  NULL,
    phone nvarchar(10) unique,
    address nvarchar(255),
	created_at DATETIME  default current_timestamp,
    updated_at DATETIME   default current_timestamp,
    is_active bit default 0,
    role_id TINYINT default 3,
     FOREIGN KEY (role_id) REFERENCES `roles`(id)
);

create table `shop`
(
	id char(36) DEFAULT(UUID()) PRIMARY KEY,
    `description` text,
    shop_address nvarchar(255),
    shop_name nvarchar(150),
    bank_number nvarchar(150),
    owner_bank nvarchar(150),
    bank_name varchar(150),
    user_id CHAR(36) NOT NULL,
    FOREIGN KEY  (user_id) REFERENCES `users`(id)
);

create table `eventcategories`
(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name nvarchar(150)
);

create table `types`
(
	id BIGINT AUTO_INCREMENT NOT  NULL PRIMARY KEY,
    name NVARCHAR(150)
);

create table `posts`
(
	id BIGINT AUTO_INCREMENT NOT  NULL PRIMARY KEY,
	name VARCHAR(255) COMMENT 'Tên sản phẩm',
    `description` LONGTEXT ,
    thumbnail NVARCHAR(255) ,
    address NVARCHAR(255) ,
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
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT, -- CHỈ RA BỨC ẢNH NÀY SẼ THUỘC SẢN PHẨM NÀO
     CONSTRAINT fk_post_images_post_id
     FOREIGN KEY (post_id) REFERENCES  posts(id) ON DELETE CASCADE, -- posts xóa thì image sẽ bị xóa theo
     image_url VARCHAR(255)
);


create table `orders`
(
	id BIGINT NOT NULL auto_increment PRIMARY KEY,
	full_name NVARCHAR(150) NOT NULL DEFAULT '',
	phone_number NVARCHAR(10) NOT NULL,
	email NVARCHAR(150) NOT NULL DEFAULT '',
	address NVARCHAR(150) NOT NULL,
	note VARCHAR(255) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	total_money FLOAT CHECK (total_money >=0),
    `status` VARCHAR(50) ,
    validation_image NVARCHAR(150),
	fee_id int,
   FOREIGN KEY (fee_id) REFERENCES `fee`(id),
	user_id CHAR(36),
   FOREIGN KEY (user_id) REFERENCES `users`(id)
);

create table `order_details`
(
	id BIGINT auto_increment not null,
    total_money float check (total_money >=0),
    order_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    primary key (id, order_id, post_id),
    foreign key (order_id) references `orders`(id),
    foreign key (post_id) references `posts`(id)
);

create table `typeandpost`
(
	id BIGint auto_increment not null,
    type_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    primary key (id, type_id, post_id),
    foreign key (type_id) references `types`(id),
    foreign key (post_id) references `posts`(id)
);

create table otp_email
(
id bigint auto_increment not null primary key,
OTP int not null,
expiry_date datetime,
user_id CHAR(36) ,
foreign key (user_id) references `users`(id)
);

CREATE TABLE transactions (
	id bigint AUTO_INCREMENT PRIMARY KEY ,
    create_at datetime,
    amount FLOAT CHECK (amount >=0),
    from_id CHAR(36),
    FOREIGN KEY (from_id) REFERENCES users(id),
    to_id CHAR(36),
    FOREIGN KEY (to_id) REFERENCES users(id),
    order_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
	`status` INT DEFAULT 0,
	`description` VARCHAR(50)
);

CREATE TABLE feedback (
	id bigint AUTO_INCREMENT PRIMARY KEY ,
    content nvarchar(255),
    rating int not null,
	customer_id CHAR(36),
    FOREIGN KEY (customer_id) REFERENCES users(id),    
	shop_id CHAR(36),
    FOREIGN KEY (shop_id) REFERENCES shop(id)
);

CREATE TABLE notification
(
	id INT PRIMARY KEY,
    `type` NVARCHAR(50)
);

CREATE TABLE user_notification
(
	id bigint AUTO_INCREMENT PRIMARY KEY,
    message NVARCHAR(255),
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	sender NVARCHAR(50),
	receiver_user CHAR(36),
    FOREIGN KEY (user_id) REFERENCES users(id),
    notification_id INT,
    FOREIGN KEY (notification_id) REFERENCES notification(id)
);

CREATE TABLE report
(
	id INT AUTO_INCREMENT PRIMARY KEY,
    problem NVARCHAR(255),
    content NVARCHAR(255),
    `status` INT,
	user_id CHAR(36),
    FOREIGN KEY (user_id) REFERENCES users(id),
	order_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES orders(id)
)

