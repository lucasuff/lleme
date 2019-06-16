set schema 'default';

drop type if exists emp cascade;

create type emp as (first_name varchar, last_name varchar);
create or replace function select_employees() returns setof emp as
'
declare
begin
drop table if exists tempregado cascade;
create temporary table tempregado as
with
    t1 as (select "FNOME", "UNOME" from "EMPREGADO"),
    t2 as (select * from t1)
select * from t2;

return query select * from tempregado;
end
'
language 'plpgsql';

select * from select_employees();