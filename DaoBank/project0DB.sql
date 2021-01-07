drop table if exists users cascade;
drop table if exists accounts cascade;
drop table if exists user_accounts cascade;

create table if not exists users(
	user_id serial primary key,
	username varchar(30),
	pass varchar(30),
	user_type varChar(30)
);

create table if not exists accounts(
	account_id serial primary key,
	account_number bigint,
	approved boolean,
	balance int

);

create table if not exists user_accounts(
	u_id int references users(user_id) on update cascade on delete cascade,
	a_id int references accounts(account_id) on update cascade,
	constraint ua_key primary key (u_id, a_id)
);

--insert into users(username, pass) values ('alec', 'pass');
--insert into users(username, pass) values ('meow', 'pass');
--insert into users(username, pass) values ('mover', 'pass');
--insert into users(username, pass) values ('alex', 'pass');

insert into accounts(account_number, approved, balance) values ('123456789012','true','2000.00');
insert into accounts(account_number, approved, balance) values ('123456789013','true','2000.00');
insert into accounts(account_number, approved, balance) values ('123456789014','true','2000.00');

create or replace procedure insert_into_users(uname varchar(30), passw varchar(60), usertype varchar(30))
language sql 
as $$
	--Here's the procedure specific logic 
	insert into users (username,pass,user_type) values (uname,passw,usertype);
$$;

call insert_into_users('jack', 'pass', 'Admin'); 
