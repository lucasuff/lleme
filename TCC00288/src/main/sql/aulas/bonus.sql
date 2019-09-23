create or replace function calcula_bonus(p_ano int, p_mat int)
returns double as $$
declare
    v_bonus double;
begin
    v_bonus = 0;
    with
        t1(lucro) as (select valor as lucro
                      from lucro
                      where ano = p_ano);
        t2(salario) as (select valor as salario
                        from salario
                        where matricula = p_matricula)
    select lucro * 0.01 + salario * 0.05 into v_bonus from t1, t2;
    if not found then
        raise notice ''
    end if
    return v_bonus;
end;$$ language plpgsql;


