show databases;
create database network default CHARACTER SET UTF8;
use network;
show tables;

/*
create table info (
    user_id varchar(20) primary key,
    id varchar(20),
	ip varchar(20),
	logined_log varchar(20),
    login_count int,
    foreign key(id) references user(id) ON UPDATE CASCADE
);


create table ranking (
	rank_id varchar(20) primary key,
    id varchar(20),
	win int default '0',
	lose int default '0',
	foreign key(id) references user(id) ON UPDATE CASCADE
);


drop table ranking;
drop table info;
*/


create table user (
	id varchar(20) primary key,
	password varchar(20) not null,
	name varchar(10) not null,
    nickname varchar(20) not null,
    email varchar(35) not null, 
    sns varchar(100) not null
);


alter table user add ip varchar(20);
alter table user add logined_log varchar(20);
alter table user add login_count int default '0';
alter table user add win int default '0';
alter table user add lose int default '0';

ALTER TABLE user MODIFY login_count int default '0' AFTER logined_log;
ALTER TABLE user DROP login_count;
ALTER TABLE user MODIFY login_count int default '0';


ALTER TABLE user MODIFY password varchar(200);
ALTER TABLE user MODIFY logined_log varchar(50);
ALTER TABLE user MODIFY ip varchar(200);


select * from user; 


insert into user value ("checdfddk", sha2("123", 512), "dfsd", "dsfdsfa", "dsfds", "sdd", "asdsad", "sdfsd", 5, 0, 0);

SELECT SHA2('9', 512);

UPDATE user set password = SHA2('test', 512) where id = "siwoo";

delete from user where ip = nulluseruser;

UPDATE USER SET WIN = WIN +1 WHERE ID = "siwoo";
UPDATE USER SET WIN = WIN +1 WHERE ID = "siwoo";