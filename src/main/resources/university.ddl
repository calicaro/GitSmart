DROP TABLE IF EXISTS students
CREATE TABLE IF NOT EXISTS students (student_id int primary key, last_name varchar(30), first_name varchar(30))

DROP TABLE IF EXISTS dormitory_rooms;
CREATE TABLE IF NOT EXISTS dormitory_rooms (dormitory_name varchar(30) primary key, room_number int, capacity int);

# Populate the students table
INSERT INTO students VALUES (1, 'McCartney', 'Paul');
INSERT INTO students VALUES (2, 'Harrison', 'George');
INSERT INTO students VALUES (3, 'Lennon', 'John');
INSERT INTO students VALUES (4, 'Starr', 'Ringo');

# Populate the dormitories table
INSERT INTO dormitory_rooms VALUES (1, 'Abbey Road', 1, 2);
INSERT INTO dormitory_rooms VALUES (2, 'Abbey Road', 2, 2);
INSERT INTO dormitory_rooms VALUES (3, 'Yellow Submarine', 1, 2);
INSERT INTO dormitory_rooms VALUES (4, 'Yellow Submarine', 2, 2);
