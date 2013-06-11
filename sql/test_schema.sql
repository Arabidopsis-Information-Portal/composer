CREATE TABLE BigDecimalTest (column_1 NUMERIC(20,4) NOT NULL);

INSERT INTO BigDecimalTest VALUES(1234.5678);

CREATE TABLE NullBigDecimalTest (column_1 NUMERIC(20,4));

INSERT INTO NullBigDecimalTest VALUES(null);

CREATE TABLE BigIntegerTest (column_1 NUMERIC(12) NOT NULL);

INSERT INTO BigIntegerTest VALUES(12345678);

CREATE TABLE NullBigIntegerTest (column_1 NUMERIC(12));

INSERT INTO NullBigIntegerTest VALUES(null);

CREATE TABLE StringTest (column_1 VARCHAR(100) NOT NULL);

INSERT INTO StringTest VALUES('a test string');

CREATE TABLE NullStringTest (column_1 VARCHAR(100));

INSERT INTO NullStringTest VALUES(null);

CREATE TABLE IntegerTest (column_1 INTEGER NOT NULL);

INSERT INTO IntegerTest VALUES(12345678);

CREATE TABLE NullIntegerTest (column_1 INTEGER);

INSERT INTO NullIntegerTest VALUES(null);

CREATE TABLE BooleanTest (column_1 INTEGER NOT NULL, column_2 VARCHAR(1) NOT NULL,
                          column_3 VARCHAR(5) NOT NULL, column_4 VARCHAR(1) NOT NULL,
                          column_5 VARCHAR(3) NOT NULL);

INSERT INTO BooleanTest VALUES (1, 'T', 'true', 'y', 'yes');
INSERT INTO BooleanTest VALUES (0, 'F', 'false', 'n', 'no');

CREATE TABLE NullBooleanTest (column_1 INTEGER);

INSERT INTO NullBooleanTest VALUES (null);

CREATE TABLE TimestampTest (column_1 TIMESTAMP NOT NULL);

INSERT INTO TimestampTest VALUES (CURRENT_DATE());

CREATE TABLE NullTimestampTest (column_1 TIMESTAMP);

INSERT INTO NullTimestampTest VALUES (null);

CREATE TABLE MainTest (column_2 INTEGER NOT NULL);

INSERT INTO MainTest VALUES (1);

CREATE TABLE BigDecimalSubqueryTest (column_1 NUMERIC(12,4) NOT NULL, column_2 INTEGER NOT NULL);

INSERT INTO BigDecimalSubqueryTest VALUE (1234.5678, 1);

CREATE TABLE BigIntegerSubqueryTest (column_1 NUMERIC(12) NOT NULL, column_2 INTEGER NOT NULL);

INSERT INTO BigIntegerSubqueryTest VALUE (12345678, 1);

CREATE TABLE BooleanSubqueryTest (column_1 VARCHAR(1) NOT NULL, column_2 INTEGER NOT NULL);

INSERT INTO BooleanSubqueryTest VALUE ('T', 1);

CREATE TABLE IntegerSubqueryTest (column_1 INTEGER NOT NULL, column_2 INTEGER NOT NULL);

INSERT INTO IntegerSubqueryTest VALUE (12345678, 1);

CREATE TABLE StringSubqueryTest (column_1 VARCHAR(100) NOT NULL, column_2 INTEGER NOT NULL);

INSERT INTO StringSubqueryTest VALUE ('a test string', 1);

CREATE TABLE TimestampSubqueryTest (column_1 TIMESTAMP NOT NULL, column_2 INTEGER NOT NULL);

INSERT INTO TimestampSubqueryTest VALUE ('2012-01-01 01:01:01', 1);

CREATE TABLE ParameterizedSubqueryTest (column_1 VARCHAR(10) NOT NULL, column_2 VARCHAR(10) NOT NULL, column_3 INTEGER NOT NULL);

INSERT INTO ParameterizedSubqueryTest VALUE ('aaa', 'bbb', 1);

COMMIT;
