drop function if exists operar(m int[][], l1 int, l2 int, c1 int, c2 int);
create or replace function operar(m int[][], l1 int, l2 int, c1 int, c2 int)
returns int[][] as $$
declare

begin
    for j in 1..array_length(m,2) loop
        m[l1][j] = m[l1][j] * c1 + m[l2][j] * c2;
    end loop;
    return m;
end;$$ language plpgsql;

select * from operar ('{{1,1,1},{1,1,1},{1,1,1}}',2,1,3,2);
