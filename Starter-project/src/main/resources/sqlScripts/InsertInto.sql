
--kredit
insert into kredit
values (1, 'naziv1', 'oznaka1', 'opis1');

insert into kredit
values (2, 'naziv2', 'oznaka2', 'opis2');

insert into kredit
values (3, 'naziv3', 'oznaka3', 'opis3');

insert into kredit
values (4, 'naziv2', 'oznaka4', 'opis4');

insert into kredit
values (-100, 'zaTestiranje', 'testOznaka', 'testOpis');

--tip racuna
insert into tip_racuna
values (1, 'tip1', 'oznaka1', 'opis1');

insert into tip_racuna
values (2, 'tip2', 'oznaka2', 'opis2');

insert into tip_racuna
values (3, 'tip3', 'oznaka3', 'opis3');

insert into tip_racuna
values (4, 'tip4', 'oznaka4', 'opis4');

insert into tip_racuna
values (-100, 'Test tip', 'Test oznaka', 'Test opis');

--klijent

insert into klijent
values (1, 'steva', 'jobic', 532, 1);

insert into klijent
values (2, 'mita', 'ruzic', 421, 2);

insert into klijent
values (3, 'joca', 'peric', 212, 3);

insert into klijent
values (4, 'nikola', 'jerkovic', 333, 4);

insert into klijent
values (-100, 'test ime', 'test prezime', 777, -100);

--racun
insert into racun
values (1, 'petrovici', 'oznaka1', 'neki racun od petrovica', 1,1);

insert into racun
values (2, 'pterodaktilovic', 'oznaka2', 'neki racun od dinosaurusa!', 2,2);

insert into racun
values (3, 'racun3', 'oznaka3', 'opis3!', 3,3);
insert into racun
values (4, 'racun4', 'oznaka4', 'opis4!', 4,4);

insert into racun
values (-100, 'test', 'test', 'test', -100, -100);