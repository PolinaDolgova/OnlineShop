--Base roles
INSERT INTO roles (role_id, role_name) VALUES (1,'ROLE_ADMIN')
ON CONFLICT (role_id) DO NOTHING ;
INSERT INTO roles (role_id, role_name) VALUES (2,'ROLE_STOREKEEPER')
ON CONFLICT (role_id) DO NOTHING ;
INSERT INTO roles (role_id, role_name) VALUES (3,'ROLE_CUSTOMER')
ON CONFLICT (role_id) DO NOTHING ;
INSERT INTO roles (role_id, role_name) VALUES (4,'ROLE_COURIER')
ON CONFLICT (role_id) DO NOTHING ;

--base login admin and password 12345678
--This account has the following role: ROLE_ADMIN, ROLE_USER
INSERT INTO users (user_id, user_name, user_surname, user_password, user_address, user_dob, user_email) VALUES (1,'admin', 'admin', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG','test','test','admin')
ON CONFLICT (user_id) DO NOTHING ;

--Roles of user admin
INSERT INTO user_roles (user_id, role_id) VALUES (1,1)
ON CONFLICT (user_id,role_id)DO NOTHING;


--Base statuses
INSERT INTO status (status_id, status_name) VALUES (1,'Sell')
ON CONFLICT (status_id)DO NOTHING;
INSERT INTO status (status_id, status_name) VALUES (2,'Unsold')
ON CONFLICT (status_id)DO NOTHING;
INSERT INTO status (status_id, status_name) VALUES (3,'Warehouse')
ON CONFLICT (status_id)DO NOTHING;
INSERT INTO status (status_id, status_name) VALUES (4,'Shipped')
ON CONFLICT (status_id)DO NOTHING;


--Base unit of measurement
INSERT INTO unit_of_measurement (unit_id, unit_name) VALUES (1,'PCS')
ON CONFLICT (unit_id)DO NOTHING;
INSERT INTO unit_of_measurement (unit_id, unit_name) VALUES (2, 'M')
ON CONFLICT (unit_id)DO NOTHING;

--Base warehouse
INSERT INTO warehouse (zone_id, zone_name) VALUES (1,'A-1')
ON CONFLICT (zone_id)DO NOTHING;
INSERT INTO warehouse (zone_id, zone_name) VALUES (2,'B-1')
ON CONFLICT (zone_id)DO NOTHING;

--Base producer
INSERT INTO producer (producer_id, producer_name) VALUES (1,'Sony')
ON CONFLICT (producer_id)DO NOTHING;
INSERT INTO producer (producer_id, producer_name) VALUES (2,'Asus')
ON CONFLICT (producer_id)DO NOTHING;