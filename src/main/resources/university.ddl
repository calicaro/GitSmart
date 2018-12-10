DROP TABLE IF EXISTS students
CREATE TABLE IF NOT EXISTS students (student_id int primary key, last_name varchar(30), first_name varchar(30), hometown varchar(30));
DROP TABLE IF EXISTS dormitory_rooms;
CREATE TABLE IF NOT EXISTS dormitory_rooms (room_id int primary key, dormitory_name varchar(30), room_number int, capacity int);

# Populate the students table
INSERT INTO students VALUES (1, 'McCartney', 'Paul','Liverpool');
INSERT INTO students VALUES (2, 'Harrison', 'George','Liverpool');
INSERT INTO students VALUES (3, 'Lennon', 'John','Manchester');
INSERT INTO students VALUES (4, 'Starr', 'Ringo','London');

# Populate the dormitories table
INSERT INTO dormitory_rooms VALUES (1, 'Abbey Road', 1, 2);
INSERT INTO dormitory_rooms VALUES (2, 'Abbey Road', 2, 2);
INSERT INTO dormitory_rooms VALUES (3, 'Yellow Submarine', 1, 2);
INSERT INTO dormitory_rooms VALUES (4, 'Yellow Submarine', 2, 2);
