DROP TABLE IF EXISTS INVENTORY;
DROP TABLE IF EXISTS PASSENGER;
DROP TABLE IF EXISTS BOOKING_RECORD;

CREATE TABLE IF NOT EXISTS `booking_record` (
  `id`           BIGINT(20) NOT NULL AUTO_INCREMENT,
  `booking_date` DATETIME            DEFAULT NULL,
  `bus_number`   VARCHAR(255)        DEFAULT NULL,
  `destination`  VARCHAR(255)        DEFAULT NULL,
  `origin`       VARCHAR(255)        DEFAULT NULL,
  `price`        VARCHAR(255)        DEFAULT NULL,
  `status`       VARCHAR(255)        DEFAULT NULL,
  `trip_date`    VARCHAR(255)        DEFAULT NULL,
  `created_time`      DATETIME            DEFAULT NULL,
  `created_by`   VARCHAR(255)        DEFAULT NULL,
  `enabled`      INT(11)             DEFAULT NULL,
  `updated_time`      DATETIME            DEFAULT NULL,
  `updated_by`   VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `passenger` (
  `id`         BIGINT(20) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(255)        DEFAULT NULL,
  `gender`     VARCHAR(255)        DEFAULT NULL,
  `last_name`  VARCHAR(255)        DEFAULT NULL,
  `booking_id` BIGINT(20)          DEFAULT NULL,
  `created_time`    DATETIME            DEFAULT NULL,
  `created_by` VARCHAR(255)        DEFAULT NULL,
  `enabled`    INT(11)             DEFAULT NULL,
  `updated_time`    DATETIME            DEFAULT NULL,
  `updated_by` VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT FOREIGN KEY (`booking_id`) REFERENCES `booking_record` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `inventory` (
  `id`         BIGINT(20) NOT NULL AUTO_INCREMENT,
  `available`  INT(11)    NOT NULL,
  `bus_number` VARCHAR(255)        DEFAULT NULL,
  `trip_date`  VARCHAR(255)        DEFAULT NULL,
  `created_time`    DATETIME            DEFAULT NULL,
  `created_by` VARCHAR(255)        DEFAULT NULL,
  `enabled`    INT(11)             DEFAULT NULL,
  `updated_time`    DATETIME            DEFAULT NULL,
  `updated_by` VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;