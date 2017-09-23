create table user (
  id bigint auto_increment primary key,
  age int not null,
  name varchar(255) null,
  password varchar(32) null
);

INSERT INTO otushw.user (age, name, password) VALUES (25, 'user', '12345678');
INSERT INTO otushw.user (age, name, password) VALUES (32, 'admin', '34567890');
INSERT INTO otushw.user (age, name, password) VALUES (27, 'alice', '4353453');
INSERT INTO otushw.user (age, name, password) VALUES (42, 'bob', '43543545');
INSERT INTO otushw.user (age, name, password) VALUES (23, 'pinky', '3241342');