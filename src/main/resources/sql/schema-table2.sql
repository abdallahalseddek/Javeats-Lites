DROP TABLE  IF EXISTS test_table2;
CREATE TABLE test_table2 (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(50) NOT NULL UNIQUE
);
/