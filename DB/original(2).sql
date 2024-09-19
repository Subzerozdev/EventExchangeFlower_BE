create database eventflowerexchange;
use eventflowerexchange;
create table `role`
(
	id int auto_increment not null primary key,
    role_name nvarchar(50) not null
);

insert into `role`(role_name) value("Admin");
insert into `role`(role_name) value("Seller");
insert into `role`(role_name) value("Customer");

create table `user`
(
	id int auto_increment not null primary key,
    email nvarchar(100) not null unique,
    `password` nvarchar(100) not null,
    full_name nvarchar(100) not null,
    phone nvarchar(10) unique,
    address nvarchar(250) not null,
    register_date datetime,
    roleID int not null,
    foreign key (roleID) references `role`(id)
);

create table `blog`
(
	id int auto_increment not null primary key,
    title nvarchar(100),
    content nvarchar(250) ,
    create_date datetime,
    userID int not null UNIQUE,
    foreign key (userID) references `user`(id)
);

create table `eventcategory`
(
	id int auto_increment not null primary key,
    category_name nvarchar(100)
);

create table `flowertype`
(
	id int auto_increment not null primary key,
    type_name nvarchar(100)
);

create table `flowercolor`
(
	id int auto_increment not null primary key,
    color_name nvarchar(100)
);

create table `post`
(
	id int auto_increment not null primary key,
    title nvarchar(100),
    `description` text ,
    address nvarchar(250) ,
    start_date datetime,
    end_date datetime,
    image nvarchar(1000) ,
    price bigint ,
    discount bigint ,
    is_deleted bit,
    userID int not null,
    eventCategoryID int not null,
    foreign key (userID) references `user`(id),
    foreign key (eventCategoryID) references `eventcategory`(id)
);

create table `payment`
(
	id int auto_increment not null primary key,
    payment_method nvarchar(100)
);

create table `order`
(
	id int auto_increment not null primary key,
    `date` datetime,
    total bigint ,
    `status` bigint ,
    userOrderID int not null,
    paymentID int not null,
    foreign key (userOrderID) references `user`(id),
    foreign key (paymentID) references `payment`(id)
);

create table `orderdetail`
(
	id int auto_increment not null,
    quantity int,
    total bigint ,
    orderID int not null,
    postID int not null,
    primary key (id, orderID, postID),
    foreign key (orderID) references `order`(id),
    foreign key (postID) references `post`(id)
);

create table `flowertypeinpost`
(
	id int auto_increment not null,
    flowerTypeID int not null,
    postID int not null,
    primary key (id, flowerTypeID, postID),
    foreign key (flowerTypeID) references `flowertype`(id),
    foreign key (postID) references `post`(id)
);

create table `flowercolorofflowertype`
(
	id int auto_increment not null,
    flowerColorID int not null,
    flowerTypeID int not null,
    primary key (id, flowerColorID, flowerTypeID),
    foreign key (flowerColorID) references `flowercolor`(id),
    foreign key (flowerTypeID) references `flowertype`(id)
);