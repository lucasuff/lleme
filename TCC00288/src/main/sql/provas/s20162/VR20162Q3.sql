CREATE TABLE emp_audit (
  operacao text NOT NULL,
  data_hora timestamp NOT NULL,
  usuario text NOT NULL,
  ssn integer NOT NULL,
  nome integer NOT NULL,
  endereco character varying NOT NULL);



CREATE ROLE lapaesleme PASSWORD 'pass' LOGIN INHERIT;
CREATE ROLE auditores;
GRANT auditores TO lapaesleme;
GRANT all ON public.emp_audit TO lapaesleme;

ALTER TABLE emp_audit ENABLE ROW LEVEL SECURITY;

CREATE POLICY p1 ON emp_audit FOR all USING (usuario=current_user) WITH CHECK (false);
CREATE POLICY p2 ON emp_audit TO auditores USING (true) WITH CHECK (true);
