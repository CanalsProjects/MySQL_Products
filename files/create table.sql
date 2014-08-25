CREATE TABLE `products`(
	`pid` int(11) primary key auto_increment,
	`name` varchar(100) not null,
	`price` decimal(10,2) not null,
	`description` text,
	`created_at` timestamp default now(),
	`updated_at` timestamp
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;