DROP USER IF EXISTS 'churchwebsite'@'%';

CREATE USER 'churchwebsite'@'localhost' IDENTIFIED BY 'churchwebsite';

GRANT ALL PRIVILEGES ON * . * TO 'churchwebsite'@'localhost';