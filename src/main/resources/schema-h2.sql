/* HOTEL */
DROP TABLE IF EXISTS HOTEL;

CREATE TABLE IF NOT EXISTS HOTEL(
  HOTEL_ID VARCHAR(255) NOT NULL,
  HOTEL_NM VARCHAR(255) NOT NULL,
  INSERT_DT DATE,
  UPDATE_DT DATE
);

ALTER TABLE HOTEL ADD CONSTRAINT PK_HOTEL  PRIMARY KEY(HOTEL_ID);

/* 예약현황 */
DROP TABLE IF EXISTS BOOKING;

CREATE TABLE IF NOT EXISTS BOOKING(
  HOTEL_ID VARCHAR(255) NOT NULL,
  BOOKING_DT DATE NOT NULL, 
  ROOM_NO VARCHAR(255) NOT NULL,
  ROOM_NM VARCHAR(255),
  INSERT_DT DATE,
  UPDATE_DT DATE
);

ALTER TABLE BOOKING ADD CONSTRAINT PK_BOOKING  PRIMARY KEY(HOTEL_ID, BOOKING_DT, ROOM_NO);

/* 숙소정보 */
DROP TABLE IF EXISTS ROOM;

CREATE TABLE IF NOT EXISTS ROOM (
  HOTEL_ID VARCHAR(255) NOT NULL,
  ROOM_NO VARCHAR(255) NOT NULL,
  ROOM_NM VARCHAR(255) NOT NULL,
  ROOM_TYPE VARCHAR(255) NOT NULL, 
  ROOM_TYPE_NM VARCHAR(255) NOT NULL, 
  OCCUPANCY VARCHAR(255),
  SPACE VARCHAR(255),
  INFO VARCHAR(255),
  PRICE BIGINT DEFAULT 0 NOT NULL,
  PEAK_PRICE BIGINT DEFAULT 0 NOT NULL, 
  INSERT_DT DATE,
  UPDATE_DT DATE
);

ALTER TABLE ROOM ADD CONSTRAINT PK_ROOM  PRIMARY KEY(HOTEL_ID, ROOM_NO);

