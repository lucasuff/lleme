CREATE TABLE country(
  code TEXT NOT NULL,
  "name" TEXT NOT NULL,
  capital TEXT NOT NULL,
  population TEXT NOT NULL,
  CONSTRAINT country_pk PRIMARY KEY
  (code)
;

CREATE TABLE province(
  "name" TEXT NOT NULL,
  country TEXT NOT NULL,
  area REAL NOT NULL,
  population INTEGER NOT NULL,
  capital TEXT NOT NULL,
  CONSTRAINT province_pk PRIMARY KEY
  ("name"),
  CONSTRAINT province_country_fk FOREIGN
  KEY (country) REFERENCES country (code)
);

CREATE OR REPLACE FUNCTION computeMedianArea(p_country VARCHAR) RETURNS NUMERIC AS $$
    DECLARE
        r1 RECORD;
        r2 RECORD;
        count INT;
        i INT;
        median NUMERIC;
        curs CURSOR FOR SELECT province.area
                        FROM country JOIN province
                        ON country.code = province.country
                        WHERE country.name = p_country
                        ORDER BY province.area;
    BEGIN
        i := 0;
        median := 0;
        IF p_country IS NOT NULL THEN
            SELECT COUNT(DISTINCT p.name)
            INTO count
            FROM country c JOIN province p ON c.code = p.country
            WHERE c.name = p_country;
            OPEN curs;
            LOOP FETCH curs INTO r1;
                EXIT WHEN NOT FOUND;
                i := i + 1;
                IF i = ROUND(count::numeric/2) THEN
                    IF count%2 = 0 THEN
                        FETCH curs into r2;
                        median = (r1.area + r2.area)/2;
                    ELSE
                        median = r1.area;
                    END IF;
                END IF;
            END LOOP;
            CLOSE curs;
        END IF;
        RETURN median;
    END; $$ LANGUAGE PLPGSQL;
