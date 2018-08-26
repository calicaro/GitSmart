DROP TABLE students;
CREATE TABLE students (student_id int primary key, last_name varchar(30), first_name varchar(30));

DROP TABLE dormitory_rooms;
CREATE TABLE dormitory_rooms (dorm_id int primary key, dormitory_name varchar(30), int room_number, int capacity);

# Populate the students table
INSERT INTO students (1, 'McCartney', 'Paul'");
INSERT INTO students (2, 'Harrison', 'George'");
INSERT INTO students (3, 'Lennon', 'John'");
INSERT INTO students (4, 'Starr', 'Ringo'");

# Populate the dormitories table
INSERT INTO dormitory_rooms values (1, 'Abbey Road', 1, 2);
INSERT into dormitory_rooms values (2, 'Abbey Road', 2, 2);
