DROP DATABASE IF EXISTS `church_website`;
CREATE DATABASE `church_website`;
USE `church_website`;

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
    `address_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `street` VARCHAR(255) NOT NULL,
    `building_no` VARCHAR(20) DEFAULT NULL,
    `house_no` VARCHAR(20) DEFAULT NULL,
    `city` VARCHAR(255) NOT NULL,
    `state` VARCHAR(100) DEFAULT NULL,
    `country` VARCHAR(100) NOT NULL,
    `post_code` VARCHAR(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile` (
    `user_profile_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `first_name` VARCHAR(100) DEFAULT NULL,
    `last_name` VARCHAR(100) DEFAULT NULL,
    `spiritual_father_name` VARCHAR(100) DEFAULT NULL,
    `christian_name` VARCHAR(100) DEFAULT NULL,
    `gender` VARCHAR(10) DEFAULT NULL,
    `dob` DATE DEFAULT NULL,
    `phone_number` VARCHAR (20) DEFAULT NULL,
    `profile_photo` VARCHAR(255) DEFAULT NULL,
    `description` MEDIUMTEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `user_profile_id` INT DEFAULT NULL,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `registration_time` DATETIME DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_blocked` BOOLEAN DEFAULT FALSE ,
    FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile`(`user_profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- username: admin -- Password: Admin123 
INSERT INTO user(user_id, user_profile_id, username, password, registration_time, is_active, is_blocked) VALUES (1, NULL, 'admin', '$2y$10$zwsA0q6vRXXyyPeYZ/6HyOCCt.hpDdJreguANYy2hqZpW2WdAaQia', CURDATE(), 1, 0);


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `role_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `role_name` VARCHAR(255) NOT NULL, 
    `role_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO role (role_id, role_name, role_description) VALUES (1, 'ROLE_ADMIN', 'Admin of the website');
INSERT INTO role (role_id, role_name, role_description) VALUES (2, 'ROLE_CLERGY', 'Clergy of the church');
INSERT INTO role (role_id, role_name, role_description) VALUES (3, 'ROLE_MEMBER', 'People attending the church');

USE `church_website`;
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
    `user_role_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `role_id` INT NOT NULL,
    `user_id` INT NOT NULL,
--     `admin_id` INT DEFAULT NULL,
--     `assigned_on` DATETIME DEFAULT NULL,
    UNIQUE KEY `UK_user_role_1` (`role_id`, `user_id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO user_role (user_role_id, role_id, user_id) VALUES (1, 1, 1);

DROP TABLE IF EXISTS `membership_amount`;
CREATE TABLE `membership_amount` (
    `membership_amount_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `membership_amount` DECIMAL(15, 2) DEFAULT 0.0,
    `membership_amount_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `member`;
DROP TABLE IF EXISTS `member_spouse`;
CREATE TABLE `member_spouse`(
	`spouse_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `baptismal_name` VARCHAR(100) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `phone_number` VARCHAR(50) DEFAULT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `member_dependent`;
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
    `member_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `baptismal_name` VARCHAR(100) DEFAULT NULL,
    `father_confessor_name` VARCHAR(255) DEFAULT NULL,
    `spouse_id` INT DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `phone_number` VARCHAR(20) DEFAULT NULL,
    `gender` VARCHAR(10) DEFAULT "MALE",
    `is_active` BOOLEAN DEFAULT TRUE,
    `membership_date` DATE NOT NULL,
    `membership_amount` DECIMAL NOT NULL,
	`payment_method` VARCHAR(30) NOT NULL,
    `direct_debit_account`  VARCHAR(30)  DEFAULT NULL,
    `direct_debit_sort_code`  VARCHAR(30)  DEFAULT NULL,
    `user_id` INT DEFAULT NULL,
    `address_id` INT DEFAULT NULL,
    FOREIGN KEY (`spouse_id`) REFERENCES `member_spouse`(`spouse_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
    FOREIGN KEY (`address_id`) REFERENCES `address`(`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`(
	`notification_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `notification_subject` VARCHAR(255) NOT NULL,
    `notification_message` TEXT NOT NULL,
    `notification_sent_time` DATETIME NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `member_dependent`;
CREATE TABLE `member_dependent`(
	`dependent_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `baptismal_name` VARCHAR(100) NOT NULL,
    `relationship_to_member` VARCHAR(30) DEFAULT NULL,
    `gender` VARCHAR(10) DEFAULT NULL,
    `age` INT DEFAULT 1,
    `member_id` INT NOT NULL,
    FOREIGN KEY(`member_id`) REFERENCES `member`(`member_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
    `blog_category_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `blog_category_name` VARCHAR(255) NOT NULL UNIQUE,
    `blog_category_description` TEXT DEFAULT NULL,
    `spouse_spiritual_name` VARCHAR(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
    `blog_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `blog_title` VARCHAR(255) NOT NULL,
    `blog_text` LONGTEXT NOT NULL,
    `blog_time` DATETIME DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_archived` BOOLEAN DEFAULT FALSE,
    `blog_category_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    FOREIGN KEY (`blog_category_id`) REFERENCES `blog_category`(`blog_category_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
    `comment_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `comment` MEDIUMTEXT NOT NULL,
    `comment_time` DATETIME NOT NULL,
    `blog_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    FOREIGN KEY (`blog_id`) REFERENCES `blog`(`blog_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `blog_comment_reply`;
CREATE TABLE `blog_comment_reply` (
	`reply_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `blog_comment_id` INT NOT NULL,
    `comment_reply_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    `reply_text` MEDIUMTEXT NOT NULL,
    `reply_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(`blog_comment_id`) REFERENCES `blog_comment`(`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(`comment_reply_id`) REFERENCES `blog_comment_reply`(`reply_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `attachment_type`;
CREATE TABLE `attachment_type` (
    `attachment_type_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `attachment_type_name` VARCHAR(255) NOT NULL,
    `attachment_type_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO attachment_type (attachment_type_id, attachment_type_name, attachment_type_description) VALUES (1, 'PDF', 'PDF files');
INSERT INTO attachment_type (attachment_type_id, attachment_type_name, attachment_type_description) VALUES (2, 'Word', 'Voice Only files');
INSERT INTO attachment_type (attachment_type_id, attachment_type_name, attachment_type_description) VALUES (3, 'Other', 'Other files, such as other text files, executables, etc.');

USE church_website;
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album` (
    `album_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `album_name` VARCHAR(255) NOT NULL,
    `album_description` TEXT DEFAULT NULL,
    `creation_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `is_archived` BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO album(album_id, album_name, album_description, is_archived) VALUES (1, 'Blog', 'Files for blogs post', false);
INSERT INTO album(album_id, album_name, album_description, is_archived) VALUES (2, 'News', 'Files for News', false);
INSERT INTO album(album_id, album_name, album_description, is_archived) VALUES (3, 'Information', 'Files for Information', false);
INSERT INTO album(album_id, album_name, album_description, is_archived) VALUES (4, 'General', 'General files', false);
								
-- DROP TABLE IF EXISTS `media`;
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
    `image_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `image_name` VARCHAR(100) DEFAULT NULL,
    `image_path` VARCHAR(255) NOT NULL,
    `upload_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `album_id` INT NOT NULL,
    -- `media_type` VARCHAR(20) DEFAULT NULL,
    `uploaded_by` INT DEFAULT NULL,
    `is_archived` BOOLEAN DEFAULT FALSE,
     FOREIGN KEY (`album_id`) REFERENCES `album`(`album_id`),
    FOREIGN KEY (`uploaded_by`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
    `video_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `video_name` VARCHAR(100) DEFAULT NULL,
    `youtube_video_id` VARCHAR(255) NOT NULL,
    `upload_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    -- `album_id` INT NOT NULL,
    -- `media_type` VARCHAR(20) DEFAULT NULL,
    `uploaded_by` INT DEFAULT NULL,
    `is_archived` BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (`uploaded_by`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
    `attachment_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `attachment_name` VARCHAR(100) DEFAULT NULL,
    `attachment_path` VARCHAR(255) NOT NULL,
    `attachment_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`attachment_type_id` INT NOT NULL,
    `uploaded_by` INT DEFAULT NULL,
    `is_archived` BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (`attachment_type_id`) REFERENCES `attachment_type`(`attachment_type_id`),
    FOREIGN KEY (`uploaded_by`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- media_for DROP TABLE IF EXISTS `event_location`;
-- CREATE TABLE `event_location` (
--     `location_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
--     `street` VARCHAR(255) NOT NULL,
--     `building_no` VARCHAR(50) DEFAULT NULL,
--     `house_no` VARCHAR(50) DEFAULT NULL,
--     `city` VARCHAR(255) NOT NULL,
--     `state` VARCHAR(255) DEFAULT NULL,
--     `country` VARCHAR(255) NOT NULL,
--     `post_code` VARCHAR(50) DEFAULT NULL
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
    `event_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `event_title` VARCHAR(255) NOT NULL,
    `event_description` MEDIUMTEXT DEFAULT NULL,
    `event_date` DATE DEFAULT NULL,
    `event_time` TIME DEFAULT NULL,
    `is_featured` BOOLEAN DEFAULT FALSE,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_archived` BOOLEAN DEFAULT FALSE,
    `event_location_id` INT NOT NULL,
    FOREIGN KEY (`event_location_id`) REFERENCES `address`(`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
    `news_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `news_title` VARCHAR(255) NOT NULL,
    `news_text` MEDIUMTEXT DEFAULT NULL,
    `news_post_time` DATETIME DEFAULT NULL,
    `is_featured` BOOLEAN DEFAULT FALSE,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_archived` BOOLEAN DEFAULT FALSE,
    `posted_by` INT NOT NULL,
    FOREIGN KEY (`posted_by`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

USE church_website;
DROP TABLE IF EXISTS `landing_content`;
CREATE TABLE `landing_content` (
    `landing_content_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `landing_content_title` VARCHAR(255) NOT NULL,
    `content` MEDIUMTEXT DEFAULT NULL,
    `content_creation_time` DATETIME DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_featured` BOOLEAN DEFAULT FALSE,
    `is_archived` BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `payment_method`;
CREATE TABLE `payment_method` (
    `payment_method_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `payment_method_name` VARCHAR(255) NOT NULL UNIQUE,
    `payment_method_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO payment_method (payment_method_id, payment_method_name, payment_method_description) VALUES (1, 'Credit card', 'Credit card paymnet');
INSERT INTO payment_method (payment_method_id, payment_method_name, payment_method_description) VALUES (2, 'PayPal', 'PayPal paymnet');
INSERT INTO payment_method (payment_method_id, payment_method_name, payment_method_description) VALUES (3, 'Offline', 'Offline paymnet via transfer');

DROP TABLE IF EXISTS `donation_type`;
CREATE TABLE `donation_type` (
    `donation_type_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `donation_type_name` VARCHAR(255) NOT NULL,
    `donation_type_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `donor`;
CREATE TABLE `donor` (
    `donor_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `donor_first_name` VARCHAR(100) DEFAULT NULL,
    `donor_last_name` VARCHAR(100) DEFAULT NULL,
    `donor_email` VARCHAR(100) DEFAULT NULL,
    `donor_address_id` INT DEFAULT NULL,
    FOREIGN KEY (`donor_address_id`) REFERENCES `address`(`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `donation`;
CREATE TABLE `donation` (
    `donation_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `donation_amount` DECIMAL(10, 2) DEFAULT 0.0,
    `donation_time` DATETIME NOT NULL,
    `agreed_to_terms` BOOLEAN DEFAULT FALSE,
    `donation_payment_method_id` INT NOT NULL,
    `donation_type_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    `donor_id` INT DEFAULT NULL,
    FOREIGN KEY (`donation_type_id`) REFERENCES `donation_type`(`donation_type_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
    FOREIGN KEY (`donor_id`) REFERENCES `donor`(`donor_id`),
    FOREIGN KEY (`donation_payment_method_id`) REFERENCES `payment_method`(`payment_method_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `church_contact`;
CREATE TABLE `church_contact` (
    `contact_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `contact_full_name` VARCHAR(255) DEFAULT NULL,
    `contact_phone` VARCHAR(50) DEFAULT NULL,
    `contact_email` VARCHAR(255) DEFAULT NULL,
    `contact_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `email_subscription`;
CREATE TABLE `email_subscription` (
    `subscription_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `email` VARCHAR(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `contact_us`;
CREATE TABLE `contact_us` (
    `contact_us_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `phone_number` VARCHAR(20) DEFAULT NULL,
    `message` TEXT NOT NULL,
    `message_time` DATETIME DEFAULT NULL,
    `user_id` INT DEFAULT NULL,
    `is_read` BOOLEAN DEFAULT false,
    `read_time` DATETIME DEFAULT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `church`;
CREATE TABLE `church` (
    `church_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `church_name` VARCHAR(255) NOT NULL,
    `church_description` TEXT DEFAULT NULL,
    `church_logo` VARCHAR(255) DEFAULT NULL,
    `phone_number1` VARCHAR(20) DEFAULT NULL,
    `phone_number2` VARCHAR(20) DEFAULT NULL,
    `whats_app_chat` VARCHAR(100) DEFAULT NULL,
    `whats_app_group` VARCHAR(100) DEFAULT NULL,
	`telegram_chat` VARCHAR(100) DEFAULT NULL,
    `telegram_group` VARCHAR(100) DEFAULT NULL,
    `youtube` VARCHAR(255) DEFAULT NULL,
    `facebook` VARCHAR(255) DEFAULT NULL,
    `tiktok` VARCHAR(100) DEFAULT NULL,
    `x_account` VARCHAR(100) DEFAULT NULL,
    `google_plus` VARCHAR(100) DEFAULT NULL,
    `p_interest` VARCHAR(100) DEFAULT NULL,
    `linked_in` VARCHAR(100) DEFAULT NULL,
    `reddit` VARCHAR(100) DEFAULT NULL,
    `email` VARCHAR(100) DEFAULT NULL,
    `address_id` INT NOT NULL,
    FOREIGN KEY (`address_id`) REFERENCES `address`(`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `church_banner`;
CREATE TABLE `church_banner` (
	`church_banner_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `church_banner` VARCHAR(255) NOT NULL,
    `church_id` INT NOT NULL,
    FOREIGN KEY (`church_id`) REFERENCES `church`(`church_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `church_staff`;
CREATE TABLE `church_staff` (
    `church_staff_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `church_staff_title` VARCHAR(255) NOT NULL,
    `church_staff_description` TEXT DEFAULT NULL,
    `church_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    FOREIGN KEY (`church_id`) REFERENCES `church`(`church_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `service_type`;
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
    `service_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `service_name` VARCHAR(255) NOT NULL,
    `service_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

USE `church_website`;
DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings`(
	`setting_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `setting_name` VARCHAR(100) NOT NULL UNIQUE,
    `setting_description` TEXT DEFAULT NULL,
    `setting_value_int` INT DEFAULT 0 ,
    `setting_value_char` VARCHAR(255) DEFAULT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char) VALUES (1, 'DEFAULT_PAGE_SIZE', 'The number of items listed in a page', 8, null);
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char) VALUES (2, 'LOCALE_LANGUAGE_CODE', 'Local language code', 0, 'en');
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char) VALUES (3, 'LOCALE_COUNTRY_CODE', 'Local country code', 0, 'GB');

DROP TABLE IF EXISTS `baptisim`;
CREATE TABLE `baptisim`(
	`request_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	`child_father_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `child_mother_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `child_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `child_god_parent_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `child_dob` DATETIME DEFAULT NULL,
    `email` VARCHAR(100) NOT NULL,
    `phone_number` VARCHAR(20) NOT NULL,
    `address_id` INT NOT NULL,
    `required_service` VARCHAR(50) DEFAULT "BAPTISM",
    `service_status` VARCHAR(30) DEFAULT "REQUEST",
    `request_date` DATE DEFAULT NULL,
    `message` TEXT DEFAULT NULL,
    FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `holy_matrimony`;
CREATE TABLE `holy_matrimony`(
	`request_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	`groom_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `bride_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `spiritual_father_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `matrimony_date` DATE DEFAULT NULL,
    `email` VARCHAR(100) NOT NULL,
    `phone_number` VARCHAR(20) NOT NULL,
    `address_id` INT NOT NULL,
    `required_service` VARCHAR(50) DEFAULT "MARRIAGE",
    `service_status` VARCHAR(30) DEFAULT "REQUEST",
    `request_date` DATETIME DEFAULT NULL,
    `message` TEXT DEFAULT NULL,
    FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `remembrance_prayer`;
CREATE TABLE `remembrance_prayer`(
	`request_id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	`requestor_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `christian_name_of_the_prayer_is_for` VARCHAR(100) NOT NULL UNIQUE,
    `spiritual_father_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `prayer_for_date` DATE DEFAULT NULL,
    `request_date` DATETIME DEFAULT NULL,
    `message` TEXT DEFAULT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
