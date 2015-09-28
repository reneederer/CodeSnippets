drop database codesnippets;

create database codesnippets;
use codesnippets;
create table compiler(id int primary key, name varchar(50), version varchar(20), location varchar(150));
create table programming_language(id int primary key, name varchar(30));
create table programming_language_version(id int primary key, programming_language_id int, version varchar(30), compiler_id int, foreign key(programming_language_id) references programming_language(id), foreign key(compiler_id) references compiler(id));
create table codesnippet(id int primary key, title varchar(50), programming_language_version_id int, sourcecode text, add_date date, times_used int, foreign key(programming_language_version_id) references programming_language_version(id));
create table theme(id int primary key, name varchar(70), subtheme_id int, foreign key(subtheme_id) references theme(id));
create table codesnippet_theme_link(codesnippet_id int, theme_id int, primary key(codesnippet_id, theme_id), foreign key(codesnippet_id) references codesnippet(id), foreign key(theme_id) references theme(id));


insert into theme(id, name) values(1, "Database");
insert into theme(id, name) values(2, "File");
insert into theme(id, name) values(3, "Regular Expression");
insert into theme(id, name) values(4, "String");
insert into programming_language(id, name) values(1, "C++");
insert into programming_language(id, name) values(2, "C#");
insert into programming_language(id, name) values(3, "COBOL");
insert into programming_language(id, name) values(4, "Clojure");
insert into programming_language(id, name) values(5, "Java");
insert into programming_language(id, name) values(6, "PHP");
insert into compiler(id, name, version, location) values(1, "not implemented", "1.0.0.3", "/home/rene/compiler");
insert into programming_language_version(id, programming_language_id, version, compiler_id) values(1, 3, "85", 1);
insert into codesnippet(id, title, programming_language_version_id, sourcecode, add_date, times_used) values(1, "Hello World", 1, '#include <iostream>

int main(){
    std::cout << "Hello, World!";
    return 0;
}', now(), 0);
