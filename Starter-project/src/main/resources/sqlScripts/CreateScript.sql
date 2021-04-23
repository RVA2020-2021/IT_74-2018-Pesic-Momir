drop table if exists kredit cascade;
drop table if exists klijent cascade;
drop table if exists tip_racuna cascade;
drop table if exists racun cascade;

create table kredit
(
	id integer not null,
	naziv varchar(100),
	oznaka varchar(20),
	opis varchar(500),
	constraint pk_kredit primary key (id)
);

create table klijent
(
	id integer not null,
	ime varchar(50),
	prezime varchar(50),
	broj_lk integer,
	kredit integer,
	constraint pk_klijent primary key (id),
	constraint fk_klijent_kredit foreign key (kredit) references kredit (id)
);

create table tip_racuna
(
	id integer not null,
	naziv varchar(100),
	oznaka varchar(20),
	opis varchar(500),
	constraint pk_tip_racuna primary key (id)
);

create table racun
(
	id integer not null,
	naziv varchar(100),
	oznaka varchar(20),
	opis varchar(500),
	tip_racuna integer,
	klijent integer , --mozda treba not null ovde i za tip racuna
	constraint pk_raun primary key (id),
	constraint fk_racun_tip_racuna foreign key (tip_racuna) references tip_racuna (id),
	constraint fk_racun_klijent foreign key (klijent) references klijent (id)
);

create index idpk_kredit on kredit(id);
create index idpk_klijent on klijent(id);
create index idpk_racun on racun(id);
create index idpk_tip_racuna on tip_racuna(id);

create index idfk_klijent_kredit on klijent(kredit);
create index idfk_racun_tip_racuna on racun(tip_racuna);
create index idfk_racun_klijent on racun(klijent);
