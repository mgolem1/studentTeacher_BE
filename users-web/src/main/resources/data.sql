insert into ADDRESS(CITY, COUNTRY, STREET, STREET_NUMBER, ZIP_CODE)
values ('Sinj', 'HR', 'Splitska', '1', '21230'),
       ('Split', 'HR', 'Cvite Fiskovića', '3', '21000'),
       ('Šibenik', 'HR', 'Trg Pavla Šubića', '2', '22000'),
       ('Zadar', 'HR', 'Božidara Petranovića', '8', '23000'),
       ('Sinj', 'HR', 'Trg Gojka Šuška', '4', '2123');

INSERT INTO ROLES(NAME, DESCRIPTION)
values ('ADMIN', 'ADMIN'),
       ('TEACHER', 'TEACHER'),
       ('STUDENT', 'STUDENT');

insert into USERS(FIRST_NAME, LAST_NAME, ADDRESS_ID, USERNAME, PASSWORD, EMAIL)
values ('Nina', 'Ninic', 1, 'nina', 'nina', 'nninic@gmail.com'),
       ('Sara', 'Saric', 2, 'sara', 'sara', 'sara@gmail.com'),
       ('Lara', 'Laric', 3, 'lara', 'lara', 'lara@gmail.com'),
       ('Mirna', 'Golem', 1, 'mirna', 'mirna', 'mirna@gmail.com'),
       ('Ivo', 'ivic', 1, 'ivo', 'ivo', 'ivo@gmail.com'),
       ('Niko', 'Nitkovic', 1, 'niko1', 'niko1', 'niko@gmail.com');

INSERT INTO USER_ROLES(USER_ID, ROLE_ID)
values (1, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 3),
       (5, 3),
       (6, 3);

insert into TEACHERS(USER_ID, NUMBER_OF_STUDENT)
values (1, 1),
       (2, 1),
       (3, 2);


insert into STUDENTS(USER_ID, DEPARTMENT, FIRST_CHOICE_USER_ID, SECOND_CHOICE_USER_ID, THIRD_CHOICE_USER_ID,
                     MENTOR_ID, FINAL_GRADE)
values (4, 'Informatika', 1, 2, 3, null, 3.4),
       (5, 'Matematika', 2, 1, 3, null, 4.5),
       (6, 'Fizika', 3, 2, 1, null, 4.0);

insert into COURSES(NAME)
values ('OOP'),
       ('Uvod u umjetnu inteligenciju'),
       ('PMA'),
       ('Programiranje mobilnih aplikacija');

insert into COURSE_TEACHER(COURSE_ID, TEACHER_ID)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 3);

insert into INTEREST(NAME)
values ('Artificial intelligence (AI)'),
       ('Human-computer interface'),
       ('Game design'),
       ('OOP'),
       ('Web development');


INSERT INTO student_interest(student_id, interest_id)
VALUES (4, 1),
       (4, 3),
       (5, 2);

insert into TEACHER_INTEREST(TEACHER_ID, INTEREST_ID)
values (1, 1),
       (1, 3),
       (3, 2);