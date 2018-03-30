use schedule;


-- clean up old versions
--
BEGIN;
DROP TABLE IF EXISTS EVENT;
DROP TABLE IF EXISTS EVENT_TYPES;
DROP DATABASE IF EXISTS schedule;
COMMIT;

CREATE DATABASE schedule;
USE schedule;

--
-- events
--
CREATE TABLE event (
    event_key INTEGER NOT NULL AUTO_INCREMENT,
    start VARCHAR(10),
    duration INTEGER,
    description VARCHAR(50),
    event_type INTEGER,
    CONSTRAINT PK_EVENT PRIMARY KEY (event_key)
) type=InnoDB;


CREATE INDEX eventindex ON event (event_key);


BEGIN;
INSERT INTO event (start, duration, description, event_type) values("5/5/2001", 5, "JAX 2001 Conference", 1);
INSERT INTO event (start, duration, description, event_type) values("5/12/2001", 1, "Mercedes Mawrathon", 4);
INSERT INTO event (start, duration, description, event_type) values("6/21/2001", 1, "XYZ Corp Consulting", 2);
INSERT INTO event (start, duration, description, event_type) values("6/30/2001", 5, "JBuilder Class", 2);
INSERT INTO event (start, duration, description, event_type) values("4/29/2001", 1, "Mom's Birthday", 3);
INSERT INTO event (start, duration, description, event_type) values("7/12/2001",  6, "BorCon",  1);
INSERT INTO event (start, duration, description, event_type) values("9/14/2001",  4, "Vacation",  3);
INSERT INTO event (start, duration, description, event_type) values("10/19/2002",  1, "Great Floridian Triathlon",  4);

COMMIT;

--
-- event_types table
--

CREATE TABLE event_types (
    event_type_key INTEGER NOT NULL AUTO_INCREMENT,
    event_text VARCHAR(20),
    CONSTRAINT PK_EVENT_TYPE_KEY PRIMARY KEY (event_type_key)
);

CREATE INDEX eventtypeindex ON event_types (event_type_key);


BEGIN;
INSERT INTO event_types (event_text) values("Conference");
INSERT INTO event_types (event_text) values("Business");
INSERT INTO event_types (event_text) values("Personal");
INSERT INTO event_types (event_text) values("Race");
INSERT INTO event_types (event_text) values("Other");
COMMIT;
