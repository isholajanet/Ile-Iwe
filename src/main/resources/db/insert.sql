set foreign_key_checks = 0;

truncate table learning_party;
truncate table authority;
truncate table instructor;

insert into learning_party(`id`, `email`, `password`, `enabled`)
values (123, "yomi@gmail.com", "pass123", false),
(124, "bomi@gmail.com", "pass123", false),
(125, "tomi@gmail.com", "pass123", false),
(126, "alex@gmail.com", "pass123", false),
(127, "craig@gmail.com", "pass123", false);

set foreign_key_checks = 1;

