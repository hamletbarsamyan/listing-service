/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;

# Dumping database structure for listings
CREATE DATABASE IF NOT EXISTS `listings` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `listings`;

# -------------- Currency -----------------

DROP TABLE IF EXISTS `currency`;
CREATE TABLE `currency` (
  `iso_code`   VARCHAR(3)   NOT NULL,
  `name`    VARCHAR (40) COLLATE 'utf8_unicode_ci',
  `symbol` VARCHAR(8) NULL COLLATE 'utf8_unicode_ci',
  PRIMARY KEY (`iso_code`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;

# -------------- role, permission, user -----------------

DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(12) NOT NULL,
  `name` varchar(70) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  INDEX `permission_name_idx` (`name`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
	`id` INT(12) NOT NULL,
	`default_role` bit(1) DEFAULT 0,
	`name` VARCHAR(70) NOT NULL,
	`description` TEXT NULL,
	PRIMARY KEY (`id`),
	INDEX `role_name_idx` (`name`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions` (
	`role_id` INT(12) NOT NULL,
	`permission_id` INT(12) NOT NULL,
	UNIQUE INDEX `role_id_permission_id` (`role_id`, `permission_id`),
	INDEX `role_permissions_permission_idx` (`permission_id`),
	CONSTRAINT `role_permissions_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
	CONSTRAINT `role_permissions_permission_id_fk` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id`         INT(12)      NOT NULL,
  `login`      VARCHAR(100) NOT NULL,
  `password`   VARCHAR(100) NOT NULL,
  `full_name` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
  `email`      VARCHAR(128) NOT NULL,
  `icon`       VARCHAR(128)          DEFAULT NULL,
  `last_login` TIMESTAMP    NULL     DEFAULT NULL,
  `enabled`    TINYINT(3)   NOT NULL DEFAULT '1',
  `role_id` INT(12) NOT NULL,
  `zip`       VARCHAR(128)          DEFAULT NULL,
  `created_at` DATETIME     NOT NULL,
  `changed_by` INT(12)      NOT NULL,
  `changed_at` DATETIME     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `t_user_login_uidx` (`login`),
  UNIQUE INDEX `t_user_email_uidx` (`email`),
  INDEX `t_user_enabled_idx` (`enabled`),
  INDEX `t_user_role_id_idx` (`role_id`),
  CONSTRAINT `t_user_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;

DROP TABLE IF EXISTS `t_user_contact`;
CREATE TABLE `t_user_contact` (
  `id`         INT(12)      NOT NULL,
  `contact_type`  TINYINT     NOT NULL,
  `contact`      VARCHAR(128) NOT NULL,
  `user_id`     INT(12)      NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `t_user_contact_user_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;


DROP TABLE IF EXISTS `verification_token`;
CREATE TABLE `verification_token` (
  `id`          INT(12)      NOT NULL,
  `user_id`     INT(12)      NOT NULL,
  `token`       VARCHAR(100) NULL,
  `token_type`  TINYINT     NOT NULL,
  `expiry_date` DATETIME     NOT NULL,
  `expired`  TINYINT     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `verification_token_uidx` (`token`),
  INDEX `verification_token_user_id_idx` (`user_id`),
  INDEX `verification_token_type_idx` (`token_type`),
  CONSTRAINT `verification_token_user_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
)
ENGINE = InnoDB;

# -------------- language -----------------
DROP TABLE IF EXISTS `language`;
CREATE TABLE `language` (
	`id` INT(12) NOT NULL,
	`code` VARCHAR(3) NOT NULL,
	`lang_locale` VARCHAR(5) NOT NULL,
	`logo` VARCHAR(40) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `language_code_idx` (`code`)
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `language_tr`;
CREATE TABLE `language_tr` (
	`language_id` INT(12) NOT NULL,
	`name` VARCHAR(80) COLLATE 'utf8_unicode_ci',
	`description` TEXT COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	INDEX `lang_tr_lang_id_idx` (`language_id`),
	INDEX `lang_tr_name_idx` (`name`),
	INDEX `lang_tr_locale_idx` (`locale`),
	CONSTRAINT `language_tr_lang_id_fk` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

# -------------- attribute -----------------

DROP TABLE IF EXISTS `attribute`;
CREATE TABLE `attribute` (
  `id`          INT(12)      NOT NULL,
  `attr_type`      VARCHAR(8)     NOT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `attribute_tr`;
CREATE TABLE `attribute_tr` (
	`attribute_id` INT(12) NOT NULL,
	`name` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`extra_info` VARCHAR(100)  COLLATE 'utf8_unicode_ci',
	`description` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	UNIQUE INDEX `attr_tr_id_locale_uidx` (`attribute_id`, `locale`),
	INDEX `attr_tr_name_idx` (`name`),
	INDEX `attr_tr_locale_idx` (`locale`),
	CONSTRAINT `attr_tr_attr_id_fk` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


DROP TABLE IF EXISTS `attribute_value`;
CREATE TABLE `attribute_value` (
  `id`          INT(12)      NOT NULL,
  `attribute_id`   INT(12)    NOT NULL,
  `parent_value_id`   INT(12)  ,
  `value`       VARCHAR(40),
  `sort_order`              INT(12)     DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `attr_value_attr_id_idx` (`attribute_id`),
  INDEX `attr_value_parent_value_id_idx` (`parent_value_id`),
  INDEX `attr_value_sort_order_idx` (`sort_order`),
  CONSTRAINT `attr_value_attr_id_fk` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
  ENGINE = InnoDB;

DROP TABLE IF EXISTS `attribute_value_tr`;
CREATE TABLE `attribute_value_tr` (
	`attribute_value_id` INT(12) NOT NULL,
	`value_tr` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	INDEX `attr_val_tr_attr_val_id_idx` (`attribute_value_id`),
	INDEX `attr_val_tr_locale_idx` (`locale`),
	CONSTRAINT `attr_val_tr_attr_vale_id_fk` FOREIGN KEY (`attribute_value_id`) REFERENCES `attribute_value` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

# -------------- category -----------------

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
`id`          INT(12)      NOT NULL,
`slug`        VARCHAR(100) NOT NULL,
`icon`       VARCHAR(128)          DEFAULT NULL,
`sort_order` INT(11) NOT NULL DEFAULT '0',
`parent_id`   INT(12)    ,
PRIMARY KEY (`id`),
UNIQUE INDEX `category_slug_uidx` (`slug`),
INDEX `category_slug_idx` (`slug`),
INDEX `category_sort_order_idx` (`sort_order`),
INDEX `category_parent_id_idx` (`parent_id`)
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `category_tr`;
CREATE TABLE `category_tr` (
	`category_id` INT(12) NOT NULL,
	`name` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`description` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	UNIQUE INDEX `cat_tr_id_locale_uidx` (`category_id`, `locale`),
	INDEX `cat_tr_cat_id_idx` (`category_id`),
	INDEX `cat_tr_name_idx` (`name`),
	INDEX `cat_tr_locale_idx` (`locale`),
	CONSTRAINT `category_tr_cat_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `category_tree`;
CREATE TABLE `category_tree` (
`category_id`          INT(12)      NOT NULL,
`parent_id`   INT(12)   NOT NULL ,
INDEX `category_tree_cat_idx` (`category_id`),
INDEX `category_tree_parent_id_idx` (`parent_id`),
CONSTRAINT `category_tree_cat_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT `category_tree_parent_id_fk` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `category_attribute`;
CREATE TABLE `category_attribute` (
  `category_id`   INT(12)    NOT NULL,
  `attribute_id`          INT(12)      NOT NULL,
  `parent_id`   INT(12)   ,
  `sort_order`              INT(12)     DEFAULT 0,
  UNIQUE INDEX `cat_attr_cat_id_attr_id_uidx` (`attribute_id`, `category_id`),
  INDEX `cat_attr_category_id_idx` (`category_id`),
  INDEX `cat_attr_attr_id_idx` (`attribute_id`),
  INDEX `cat_attr_parent_id_idx` (`parent_id`),
  INDEX `cat_attr_sort_order_idx` (`sort_order`),
  CONSTRAINT `cat_attr_cat_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `cat_attr_attr_id_fk` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE = InnoDB;

# -------------- location -----------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
	`id` INT(12) NOT NULL,
	PRIMARY KEY (`id`)
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `country_tr`;
CREATE TABLE `country_tr` (
	`country_id` INT(12) NOT NULL,
	`name` VARCHAR(70) COLLATE 'utf8_unicode_ci',
	`description` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	UNIQUE INDEX `country_tr_id_locale_uidx` (`country_id`, `locale`),
	INDEX `country_tr_id_idx` (`country_id`),
	INDEX `country_tr_name_idx` (`name`),
	INDEX `country_tr_locale_idx` (`locale`),
	CONSTRAINT `country_tr_country_id_fk` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
	`id` INT(12) NOT NULL,
	`country_id` INT(12) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `state_country_id_idx` (`country_id`),
	CONSTRAINT `state_country_id_fk` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `state_tr`;
CREATE TABLE `state_tr` (
	`state_id` INT(12) NOT NULL,
	`name` VARCHAR(60) COLLATE 'utf8_unicode_ci',
	`description` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	UNIQUE INDEX `state_tr_id_locale_uidx` (`state_id`, `locale`),
	INDEX `state_tr_state_id_idx` (`state_id`),
	INDEX `state_tr_name_idx` (`name`),
	INDEX `state_tr_locale_idx` (`locale`),
	CONSTRAINT `state_tr_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
	`id` INT(12) NOT NULL,
	`state_id` INT(12) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `city_state_id_idx` (`state_id`),
	CONSTRAINT `city_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `city_tr`;
CREATE TABLE `city_tr` (
	`city_id` INT(12) NOT NULL,
	`name` VARCHAR(60) COLLATE 'utf8_unicode_ci',
	`description` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	UNIQUE INDEX `city_tr_id_locale_uidx` (`city_id`, `locale`),
	INDEX `city_tr_city_id_idx` (`city_id`),
	INDEX `city_tr_name_idx` (`name`),
	INDEX `city_tr_locale_idx` (`locale`),
	CONSTRAINT `city_tr_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `district`;
CREATE TABLE `district` (
	`id` INT(12) NOT NULL,
	`city_id` INT(12) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `district_city_id_idx` (`city_id`),
	CONSTRAINT `district_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE=InnoDB;

DROP TABLE IF EXISTS `district_tr`;
CREATE TABLE `district_tr` (
	`district_id` INT(12) NOT NULL,
	`name` VARCHAR(60) COLLATE 'utf8_unicode_ci',
	`description` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	UNIQUE INDEX `district_tr_id_locale_uidx` (`district_id`, `locale`),
	INDEX `district_tr_id_idx` (`district_id`),
	INDEX `district_tr_name_idx` (`name`),
	INDEX `district_tr_locale_idx` (`locale`),
	CONSTRAINT `district_tr_district_id_fk` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

# -------------- item -----------------

DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id`          BIGINT(12)      NOT NULL,
  `category_id` INT(12) NOT NULL,
  `listing_type`      TINYINT     NOT NULL,
  `price` DECIMAL(12, 2),
  `currency`        VARCHAR(3),
  `purchase_currency`        VARCHAR(3),
  `purchase_price` DOUBLE,
  `inv_count` DECIMAL(14,4) NULL DEFAULT NULL,
  `upc` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
  `sku` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
  `active`          BOOLEAN     NOT NULL DEFAULT 1,
  `created_at` DATETIME     NOT NULL,
  `changed_at` DATETIME     NOT NULL,
  `user_id`         INT(12)     NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `item_category_id_idx` (`category_id`),
  INDEX `item_price_idx` (`price`),
  INDEX `item_upc_idx` (`upc`),
  INDEX `item_sku_idx` (`sku`),
  INDEX `item_listing_type_idx` (`listing_type`),
  INDEX `item_currency_idx` (`currency`),
  INDEX `item_active_idx` (`active`),
  INDEX `item_user_id_idx` (`user_id`),
  CONSTRAINT `item_category_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `item_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `item_tr`;
CREATE TABLE `item_tr` (
	`item_id` BIGINT(12) NOT NULL,
	`name` VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`description` TEXT COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	UNIQUE INDEX `item_id_locale_uidx` (`item_id`, `locale`),
	INDEX `item_tr_id_idx` (`item_id`),
	INDEX `item_trname_idx` (`name`),
	INDEX `item_tr_locale_idx` (`locale`),
	CONSTRAINT `item_tr_item_id_fk` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `item_address`;
CREATE TABLE `item_address` (
  `id`          BIGINT(12)      NOT NULL,
  `item_id`          BIGINT(12)      NOT NULL,
  `country_id` INT(12) NULL DEFAULT NULL,
  `state_id` INT(12) NULL DEFAULT NULL,
  `city_id` INT(12) NULL DEFAULT NULL,
  `district_id` INT(12) NULL DEFAULT NULL,
  `zip`       VARCHAR(128)          DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `item_addr_country_id_idx` (`country_id`),
  INDEX `item_addr_state_id_idx` (`state_id`),
  INDEX `item_addr_city_id_idx` (`city_id`),
  INDEX `item_addr_district_id_idx` (`district_id`),
  CONSTRAINT `item_addr_item_id_fk` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `item_address_tr`;
CREATE TABLE `item_address_tr` (
	`item_address_id` BIGINT(12) NOT NULL,
	`address_line1`  VARCHAR(255) COLLATE 'utf8_unicode_ci',
    `address_line2`  VARCHAR(255) COLLATE 'utf8_unicode_ci',
	`locale` VARCHAR(5) NOT NULL,
	UNIQUE INDEX `item_addr_tr_id_locale_uidx` (`item_address_id`, `locale`),
	INDEX `item_addr_tr_id_idx` (`item_address_id`),
	INDEX `item_addr_tr_locale_idx` (`locale`),
	CONSTRAINT `item_addr_tr_id_fk` FOREIGN KEY (`item_address_id`) REFERENCES `item_address` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `item_gps_location`;
CREATE TABLE `item_gps_location` (
  `id`          BIGINT(12)      NOT NULL,
  `item_id`          BIGINT(12)      NOT NULL,
  `latitude` VARCHAR(100) NULL DEFAULT NULL,
  `longitude` VARCHAR(100) NULL DEFAULT NULL,
  `altitude` VARCHAR(100) NULL DEFAULT NULL,
   PRIMARY KEY (`id`),
  INDEX `item_gps_loc_item_id_idx` (`item_id`),
  CONSTRAINT `item_gps_loc_item_id_fk` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `item_attribute`;
CREATE TABLE `item_attribute` (
  `item_id`   BIGINT(12)    NOT NULL,
  `attribute_id`   INT(12)    NOT NULL,
  `value` VARCHAR(40) NULL DEFAULT NULL,
  UNIQUE INDEX `item_attr_id_attr_id_uidx` (`item_id`, `attribute_id`),
  INDEX `item_attr_id_idx` (`item_id`),
  INDEX `item_attr_attr_id_idx` (`attribute_id`),
  CONSTRAINT `item_attr_item_id_fk` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `item_attr_attr_id_fk` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `item_image`;
CREATE TABLE `item_image` (
  `id`          BIGINT(12)      NOT NULL,
  `item_id`          BIGINT(12)      NOT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `sort_order`              INT(12)     DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `item_image_item_id_idx` (`item_id`),
  INDEX `item_image_sort_order_idx` (`sort_order`),
  CONSTRAINT `item_image_item_id_fk` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE = InnoDB;


# -------------- account -----------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id`              INT(12)     NOT NULL,
  `balance` DECIMAL(12, 2),
  `currency`        VARCHAR(3)  NOT NULL,
  `symbol` VARCHAR(8) NULL COLLATE 'utf8_unicode_ci',
  `user_id`         INT(12)     NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `account_currency_idx` (`currency`),
  INDEX `account_user_id_idx` (`user_id`),
  CONSTRAINT `account_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;

# -------------- transaction -----------------

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id`               BIGINT(12)        NOT NULL,
  `amount`           DECIMAL(12, 2) NOT NULL,
  `status`           TINYINT        NOT NULL,
  `transaction_type` TINYINT        NOT NULL,
  `transaction_source` TINYINT        NOT NULL,
  `transaction_date` DATETIME       NOT NULL,
  `status_change_date` DATETIME       NOT NULL,
  `description`       VARCHAR(255) COLLATE 'utf8_unicode_ci',
  `account_id`          INT(12)        NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `transaction_account_id_idx` (`account_id`),
  CONSTRAINT `transaction_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;

# -------------- reminder -----------------
DROP TABLE IF EXISTS `reminder`;
CREATE TABLE `reminder` (
  `id`               INT(12)        NOT NULL,
  `transaction_type` TINYINT        NOT NULL,
  `status`           TINYINT        NOT NULL,
  `due_date`   DATETIME       NOT NULL,
  `auto_charge`      TINYINT(1)     NOT NULL,
  `reminder_repeat`           TINYINT     NOT NULL,
  `description`       VARCHAR(255) COLLATE 'utf8_unicode_ci',
  `user_id`          INT(12)        NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `reminder_status_idx` (`status`),
  INDEX `reminder_due_date_idx` (`due_date`),
  INDEX `reminder_auto_charge_idx` (`auto_charge`),
  INDEX `reminder_repeat_idx` (`reminder_repeat`),
  INDEX `reminder_user_idx` (`user_id`),
  CONSTRAINT `reminder_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;

DROP TABLE IF EXISTS `reminder_transaction`;
CREATE TABLE `reminder_transaction` (
  `reminder_id`      INT(12)        NOT NULL,
  `transaction_id`      BIGINT(12)        NOT NULL,
  PRIMARY KEY (`reminder_id`, `transaction_id`),
  INDEX `reminder_transaction_reminder_id_idx` (`reminder_id`),
  INDEX `reminder_transaction_transaction_id_idx` (`transaction_id`),
  CONSTRAINT `reminder_transaction_reminder_id_fk` FOREIGN KEY (`reminder_id`) REFERENCES `reminder` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reminder_transaction_transaction_id_fk` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`) ON DELETE CASCADE
)
ENGINE = InnoDB;

# -------------- Events -----------------

DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `id`           BIGINT(12)  NOT NULL,
  `event_type`   TINYINT   NOT NULL,
  `message`      TEXT     NULL COLLATE 'utf8_unicode_ci',
  `performed_by` INT(12),
  `created_at`   DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `event_type_idx` (`event_type`),
  INDEX `event_performed_by_idx` (`performed_by`),
  INDEX `event_created_at_idx` (`created_at`)
)
COLLATE = 'utf8_unicode_ci'
ENGINE = InnoDB;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
