-- the first script for migration
CREATE TABLE Book (
    id bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    title varchar(255),
    authorName varchar(255),
    availablity boolean not null
    
);

   
CREATE TABLE User (
    id bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    fullname varchar(255),
    isAdmin boolean not null,
    password varchar(255),
    username varchar(255)
    
);

 
--ALTER TABLE Article_authorIds
--add foreign key (Article_id)
--references Article(id);

