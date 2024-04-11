
drop database if exists online_test;
create database if not exists online_test;

use online_test;

-- Creating base tables
create table Student(SRN varchar(50) primary key, Branch varchar(50), Name varchar(50), Password varchar(50));

create table Admin(ID varchar(50) primary key, Name varchar(50), Password varchar(50));

create table Teacher(ID varchar(50) primary key, Name varchar(50), Password varchar(50), Branch varchar(50));

create table Test(TestID varchar(50) primary key,  StartDateTime TIME,  EndDateTime TIME ,  Questions varchar(500), Answers varchar(500), Description varchar(500), Accepted bool);


CREATE TABLE Student_Test (
    SRN VARCHAR(50),
    TestID varchar(50),
    MarksSecured INT,
    MarkedAnswers VARCHAR(100),
    PRIMARY KEY (SRN, TestID)
);

 -- Altering tables

alter table Test add column Teacher_ID varchar(50);
alter table Test add foreign key (Teacher_ID) references Teacher(ID);
 
alter table Test add column Admin_ID varchar(50);
alter table Test add foreign key (Admin_ID) references Admin(ID);


alter table Student_Test add foreign key (SRN) references Student(SRN);
alter table Student_Test add foreign key (TestID) references Test(TestID);


ALTER TABLE Test ADD CONSTRAINT time_constraint CHECK(StartDateTime < EndDateTime);

-- Insert into Student table
INSERT INTO Student (SRN, Branch, Name, Password) VALUES ('SRN1', 'Computer Science', 'John Doe', 'password1');

-- Insert into Admin table
INSERT INTO Admin (ID, Name, Password) VALUES ('Admin1', 'Admin Name', 'adminpassword');

-- Insert into Teacher table
INSERT INTO Teacher (ID, Name, Password, Branch) VALUES ('Teacher1', 'Teacher Name', 'teacherpassword', 'Computer Science');


-- Insert into Test table
INSERT INTO Test (TestID, StartDateTime, EndDateTime, Questions, Answers, Description, Accepted) 
VALUES ('Test1', '08:00:00', '10:00:00', 'What is 2+2?', '4', 'Math test', false);

INSERT INTO Test (TestID, StartDateTime, EndDateTime, Questions, Answers, Description, Accepted) 
VALUES ('Test2', '10:30:00', '12:30:00', 'What is the capital of France?', 'Paris', 'Geography test', false);

INSERT INTO Test (TestID, StartDateTime, EndDateTime, Questions, Answers, Description, Accepted) 
VALUES ('Test3', '13:00:00', '15:00:00', 'Who wrote Romeo and Juliet?', 'William Shakespeare', 'Literature test', true);
 
 -- Insert into Student_Test table
INSERT INTO Student_Test (SRN, TestID, MarksSecured, MarkedAnswers) VALUES ('SRN1', 'Test1', 10, '4');
