-- Create a new user with the specified password
-- Optionally, grant privileges to the user for the new databases
GRANT ALL PRIVILEGES ON midzik_advertisement_microservice.* TO 'midzik_admin'@'%';
GRANT ALL PRIVILEGES ON midzik_authentication_microservice.* TO 'midzik_admin'@'%';
GRANT ALL PRIVILEGES ON midzik_notification_microservice.* TO 'midzik_admin'@'%';
GRANT ALL PRIVILEGES ON midzik_payment_microservice.* TO 'midzik_admin'@'%';
