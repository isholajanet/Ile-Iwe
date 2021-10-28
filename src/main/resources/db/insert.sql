create table student(id INT primary key, firstname VARCHAR(45), lastname VARCHAR(45),
                        dob DATE, gender VARCHAR(45))

insert into student(`id`, `firstname`, `lastname`, `gender`)
values(110, 'janet', 'ishola', 'Female'),
      (111, 'dunni', 'olayemi', 'Female');

insert into instructor(`id`, `firstname`, `lastname`, `gender`, `specialization`, `bio`)
values(110, 'janet', 'ishola', 'Female', 'Tech', 'A tech guru'),
      (111, 'dunni', 'olayemi', 'Female', 'BioChem', 'Micro organisms');

insert into learning_party(`id`, `email`, `password`, `enabled`)
values(110, 'janet@gmail.com', 'janet123', true),
      (111, 'dunni@gmail.com', 'olayemi12', false);

insert into course(`id`, `title`, `description`, `duration`, `language`, `bio`)
values(110, 'Python', 'A programming language', '3 months', 'French'),
      (111, 'Java', 'A programming language', '1 year', 'English');

