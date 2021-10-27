create table student(id INT primary key, firstname VARCHAR(45), lastname VARCHAR(45),
                        dob DATE, gender VARCHAR(45))

insert into student(`id`, `firstname`, `lastname`, `dob`, `gender`)
values(110, 'janet', 'ishola', '28/10/2021', 'Female'),
      (111, 'dunni', 'olayemi', '30/01/2020', 'Female');
