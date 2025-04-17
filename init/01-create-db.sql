CREATE DATABASE IF NOT EXISTS midzik_advertisement_microservice;
CREATE DATABASE IF NOT EXISTS midzik_authentication_microservice;
CREATE DATABASE IF NOT EXISTS midzik_notification_microservice;
CREATE DATABASE IF NOT EXISTS midzik_payment_microservice;

USE midzik_authentication_microservice;

CREATE TABLE IF NOT EXISTS t_roles(id INT(8) AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL);

INSERT INTO t_roles(name) VALUES("Administrator"),("Reviewer"),("User");
