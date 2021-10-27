create database ileiwedb;

create user 'root'@'localhost' identified by 'janet';
grant all privileges on ileiwedb.* to 'root'@'localhost';
flush privileges;