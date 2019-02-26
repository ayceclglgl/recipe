## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE dev;
CREATE DATABASE prod;

#Create database service accounts
CREATE USER 'devuser'@'localhost' IDENTIFIED BY 'dev';
CREATE USER 'produser'@'localhost' IDENTIFIED BY 'prod';
CREATE USER 'devuser'@'%' IDENTIFIED BY 'dev';
CREATE USER 'produser'@'%' IDENTIFIED BY 'prod';

#Database grants
grant select, insert, update, delete on dev.* to 'devuser'@'localhost';
grant select, insert, update, delete on prod.* to 'produser'@'localhost';
grant select, insert, update, delete on dev.* to 'devuser'@'%';
grant select, insert, update, delete on prod.* to 'produser'@'%';
#GRANT SELECT ON dev.* to 'devuser'@'localhost';
#GRANT INSERT ON dev.* to 'devuser'@'localhost';
#GRANT DELETE ON dev.* to 'devuser'@'localhost';
#GRANT UPDATE ON dev.* to 'devuser'@'localhost';
#GRANT SELECT ON prod.* to 'produser'@'localhost';
#GRANT INSERT ON prod.* to 'produser'@'localhost';
#GRANT DELETE ON prod.* to 'produser'@'localhost';
#GRANT UPDATE ON prod.* to 'produser'@'localhost';
#GRANT SELECT ON dev.* to 'devuser'@'%';
#GRANT INSERT ON dev.* to 'devuser'@'%';
#GRANT DELETE ON dev.* to 'devuser'@'%';
#GRANT UPDATE ON dev.* to 'devuser'@'%';
#GRANT SELECT ON prod.* to 'produser'@'%';
#GRANT INSERT ON prod.* to 'produser'@'%';
#GRANT DELETE ON prod.* to 'produser'@'%';
#GRANT UPDATE ON prod.* to 'produser'@'%';