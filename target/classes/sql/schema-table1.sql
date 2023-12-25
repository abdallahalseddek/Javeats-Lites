DROP TABLE  IF EXISTS test_table1;
CREATE TABLE test_table1 (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL UNIQUE
);
/