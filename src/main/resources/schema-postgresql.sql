DROP TABLE IF EXISTS customer;

CREATE TABLE customer  (
    ssoid VARCHAR(100),
    ts BIGINT,
    grp VARCHAR(50),
    type VARCHAR(50),
    subtype VARCHAR(50),
    url VARCHAR(250),
    orgid VARCHAR(50),
    formid VARCHAR(50),
    code VARCHAR(50),
    ltpa VARCHAR(50),
    sudirresponse VARCHAR(50),
    ymdh VARCHAR(50)
);