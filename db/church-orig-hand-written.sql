DROP DATABASE IF EXISTS `church_website`;
CREATE DATABASE `church_website`;
USE `church_website`;

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
    `address_id` INT PRIMARY KEY AUTO_INCREMENT,
    `street` VARCHAR(255) NOT NULL,
    `building_no` VARCHAR(20) DEFAULT NULL,
    `house_no` VARCHAR(20) DEFAULT NULL,
    `city` VARCHAR(255) NOT NULL,
    `state` VARCHAR(100) DEFAULT NULL,
    `country` VARCHAR(100) NOT NULL,
    `post_code` VARCHAR(20) DEFAULT NULL,
    `map_iframe` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile` (
    `user_profile_id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(100) DEFAULT NULL,
    `last_name` VARCHAR(100) DEFAULT NULL,
    `spiritual_father_name` VARCHAR(100) DEFAULT NULL,
    `christian_name` VARCHAR(100) DEFAULT NULL,
    `gender` VARCHAR(10) DEFAULT NULL,
    `dob` DATE DEFAULT NULL,
    `phone_number` VARCHAR (20) DEFAULT NULL,
    `profile_photo` VARCHAR(255) DEFAULT NULL,
    `public_id` VARCHAR(255),
    `biography` MEDIUMTEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_profile_id` INT DEFAULT NULL,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `registration_time` DATETIME DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_blocked` BOOLEAN DEFAULT FALSE ,
    FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile`(`user_profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
-- username: admin -- Password: Admin@123 
INSERT INTO user(user_id, user_profile_id, username, email, password, registration_time, is_active, is_blocked) VALUES (1, NULL, 'admin', 'zemaedot3@gmail.com', '$2y$10$ScC6toANF46NaaOlCM2KUuQio9PUKWxfk7itdM2bFLJLOgsQu54K.', CURDATE(), 1, 0);


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `role_id` INT PRIMARY KEY AUTO_INCREMENT,
    `role_name` VARCHAR(255) NOT NULL, 
    `role_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO role (role_id, role_name, role_description) VALUES (1, 'ROLE_ADMIN', 'Admin of the website');
INSERT INTO role (role_id, role_name, role_description) VALUES (2, 'ROLE_CLERGY', 'Clergy of the church');
INSERT INTO role (role_id, role_name, role_description) VALUES (3, 'ROLE_MEMBER', 'People attending the church');

USE `church_website`;
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
    `user_role_id` INT PRIMARY KEY AUTO_INCREMENT,
    `role_id` INT NOT NULL,
    `user_id` INT NOT NULL,
--     `admin_id` INT DEFAULT NULL,
--     `assigned_on` DATETIME DEFAULT NULL,
    UNIQUE KEY `UK_user_role_1` (`role_id`, `user_id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO user_role (user_role_id, role_id, user_id) VALUES (1, 1, 1);

DROP TABLE IF EXISTS `membership_amount`;
CREATE TABLE `membership_amount` (
    `membership_amount_id` INT PRIMARY KEY AUTO_INCREMENT,
    `membership_amount` DECIMAL(15, 2) DEFAULT 0.0,
    `membership_amount_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- DROP TABLE IF EXISTS `member`;
DROP TABLE IF EXISTS `member_spouse`;
CREATE TABLE `member_spouse`(
	`spouse_id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `baptismal_name` VARCHAR(100) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `phone_number` VARCHAR(50) DEFAULT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- DROP TABLE IF EXISTS `member_dependent`;
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
    `member_id` INT PRIMARY KEY AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`(
	`notification_id` INT PRIMARY KEY AUTO_INCREMENT,
    `notification_subject` VARCHAR(255) NOT NULL,
    `notification_message` TEXT NOT NULL,
    `notification_sent_time` DATETIME NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `member_dependent`;
CREATE TABLE `member_dependent`(
	`dependent_id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `baptismal_name` VARCHAR(100) NOT NULL,
    `relationship_to_member` VARCHAR(30) DEFAULT NULL,
    `gender` VARCHAR(10) DEFAULT NULL,
    `age` INT DEFAULT 1,
    `member_id` INT NOT NULL,
    FOREIGN KEY(`member_id`) REFERENCES `member`(`member_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
    `blog_category_id` INT PRIMARY KEY AUTO_INCREMENT,
    `blog_category_name` VARCHAR(255) NOT NULL UNIQUE,
    `blog_category_description` TEXT DEFAULT NULL,
    `spouse_spiritual_name` VARCHAR(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
    `blog_id` INT PRIMARY KEY AUTO_INCREMENT,
    `blog_title` VARCHAR(255) NOT NULL,
    `blog_text` LONGTEXT NOT NULL,
    `blog_time` DATETIME DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_archived` BOOLEAN DEFAULT FALSE,
    `blog_category_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    FOREIGN KEY (`blog_category_id`) REFERENCES `blog_category`(`blog_category_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
    `comment_id` INT PRIMARY KEY AUTO_INCREMENT,
    `comment` MEDIUMTEXT NOT NULL,
    `comment_time` DATETIME NOT NULL,
    `blog_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    FOREIGN KEY (`blog_id`) REFERENCES `blog`(`blog_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `blog_comment_reply`;
CREATE TABLE `blog_comment_reply` (
	`reply_id` INT PRIMARY KEY AUTO_INCREMENT,
    `blog_comment_id` INT NOT NULL,
    `comment_reply_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    `reply_text` MEDIUMTEXT NOT NULL,
    `reply_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(`blog_comment_id`) REFERENCES `blog_comment`(`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(`comment_reply_id`) REFERENCES `blog_comment_reply`(`reply_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `attachment_type`;
CREATE TABLE `attachment_type` (
    `attachment_type_id` INT PRIMARY KEY AUTO_INCREMENT,
    `attachment_type_name` VARCHAR(255) NOT NULL,
    `attachment_type_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO attachment_type (attachment_type_id, attachment_type_name, attachment_type_description) VALUES (1, 'PDF', 'PDF files');
INSERT INTO attachment_type (attachment_type_id, attachment_type_name, attachment_type_description) VALUES (2, 'Word', 'Voice Only files');
INSERT INTO attachment_type (attachment_type_id, attachment_type_name, attachment_type_description) VALUES (3, 'Other', 'Other files, such as other text files, executables, etc.');

USE church_website;
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album` (
    `album_id` INT PRIMARY KEY AUTO_INCREMENT,
    `album_name` VARCHAR(255) NOT NULL,
    `album_description` TEXT DEFAULT NULL,
    `creation_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `is_archived` BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO album(album_id, album_name, album_description, is_archived) VALUES (1, 'Blog', 'Files for blogs post', false);
INSERT INTO album(album_id, album_name, album_description, is_archived) VALUES (2, 'News', 'Files for News', false);
INSERT INTO album(album_id, album_name, album_description, is_archived) VALUES (3, 'Information', 'Files for Information', false);
INSERT INTO album(album_id, album_name, album_description, is_archived) VALUES (4, 'General', 'General files', false);
								
-- DROP TABLE IF EXISTS `media`;
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
    `image_id` INT PRIMARY KEY AUTO_INCREMENT,
    `image_name` VARCHAR(100) DEFAULT NULL,
    `image_path` VARCHAR(255) NOT NULL,
    `public_id` VARCHAR(255),
    `upload_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `album_id` INT NOT NULL,
    -- `media_type` VARCHAR(20) DEFAULT NULL,
    `uploaded_by` INT DEFAULT NULL,
    `is_archived` BOOLEAN DEFAULT FALSE,
     FOREIGN KEY (`album_id`) REFERENCES `album`(`album_id`),
    FOREIGN KEY (`uploaded_by`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
    `video_id` INT PRIMARY KEY AUTO_INCREMENT,
    `video_name` VARCHAR(100) DEFAULT NULL,
    `youtube_video_id` VARCHAR(255) NOT NULL,
    `upload_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    -- `album_id` INT NOT NULL,
    -- `media_type` VARCHAR(20) DEFAULT NULL,
    `uploaded_by` INT DEFAULT NULL,
    `is_archived` BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (`uploaded_by`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
    `attachment_id` INT PRIMARY KEY AUTO_INCREMENT,
    `attachment_name` VARCHAR(100) DEFAULT NULL,
    `attachment_path` VARCHAR(255) NOT NULL,
    `public_id` VARCHAR(255),
    `attachment_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`attachment_type_id` INT NOT NULL,
    `uploaded_by` INT DEFAULT NULL,
    `is_archived` BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (`attachment_type_id`) REFERENCES `attachment_type`(`attachment_type_id`),
    FOREIGN KEY (`uploaded_by`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- media_for DROP TABLE IF EXISTS `event_location`;
-- CREATE TABLE `event_location` (
--     `location_id` INT PRIMARY KEY AUTO_INCREMENT,
--     `street` VARCHAR(255) NOT NULL,
--     `building_no` VARCHAR(50) DEFAULT NULL,
--     `house_no` VARCHAR(50) DEFAULT NULL,
--     `city` VARCHAR(255) NOT NULL,
--     `state` VARCHAR(255) DEFAULT NULL,
--     `country` VARCHAR(255) NOT NULL,
--     `post_code` VARCHAR(50) DEFAULT NULL
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
    `event_id` INT PRIMARY KEY AUTO_INCREMENT,
    `event_title` VARCHAR(255) NOT NULL,
    `event_description` MEDIUMTEXT DEFAULT NULL,
    `event_date` DATE DEFAULT NULL,
    `event_time` TIME DEFAULT NULL,
    `is_featured` BOOLEAN DEFAULT FALSE,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_archived` BOOLEAN DEFAULT FALSE,
    `event_location_id` INT NOT NULL,
    FOREIGN KEY (`event_location_id`) REFERENCES `address`(`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
    `news_id` INT PRIMARY KEY AUTO_INCREMENT,
    `news_title` VARCHAR(255) NOT NULL,
    `news_text` MEDIUMTEXT DEFAULT NULL,
    `news_post_time` DATETIME DEFAULT NULL,
    `is_featured` BOOLEAN DEFAULT FALSE,
    `is_active` BOOLEAN DEFAULT TRUE,
    `is_archived` BOOLEAN DEFAULT FALSE,
    `posted_by` INT NOT NULL,
    FOREIGN KEY (`posted_by`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

USE church_website;
DROP TABLE IF EXISTS `landing_content`;
CREATE TABLE `landing_content` (
    `landing_content_id` INT PRIMARY KEY AUTO_INCREMENT,
    `landing_content_title` VARCHAR(255) NOT NULL,
    `content` MEDIUMTEXT DEFAULT NULL,
    `content_creation_time` DATETIME DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT FALSE,
    `is_featured` BOOLEAN DEFAULT FALSE,
    `is_archived` BOOLEAN DEFAULT FALSE,
    `content_order` INT DEFAULT 0
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `payment_method`;
CREATE TABLE `payment_method` (
    `payment_method_id` INT PRIMARY KEY AUTO_INCREMENT,
    `payment_method_name` VARCHAR(255) NOT NULL UNIQUE,
    `payment_method_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO payment_method (payment_method_id, payment_method_name, payment_method_description) VALUES (1, 'Credit card', 'Credit card paymnet');
INSERT INTO payment_method (payment_method_id, payment_method_name, payment_method_description) VALUES (2, 'Offline', 'Offline paymnet via transfer');

DROP TABLE IF EXISTS `donation_purpose`;
CREATE TABLE `donation_purpose` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `donation_purpose_name` VARCHAR(255) NOT NULL,
    `donation_purpose_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

 -- OP TABLE IF EXISTS `donor`;
-- CREATE TABLE `donor` (
--     `donor_id` INT PRIMARY KEY AUTO_INCREMENT,
--     `donor_first_name` VARCHAR(100) DEFAULT NULL,
--     `donor_last_name` VARCHAR(100) DEFAULT NULL,
--     `donor_email` VARCHAR(100) DEFAULT NULL,
--     `donor_address_id` INT DEFAULT NULL,
--     FOREIGN KEY (`donor_address_id`) REFERENCES `address`(`address_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `donation`;
CREATE TABLE `donation` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `donation_amount` DECIMAL(10, 2) DEFAULT 0.0,
    `donation_time` DATETIME NOT NULL,
    `payment_method` VARCHAR(100) NOT NULL,
    `donation_purpose_id` INT NOT NULL,
    `donor_full_name` VARCHAR(100) DEFAULT NULL,
    `donor_email` VARCHAR(100) DEFAULT NULL,
    `phone_number` VARCHAR(20) DEFAULT NULL,
    `direct_debit_sort_code` VARCHAR(35) DEFAULT NULL,
    `direct_debit_account` VARCHAR(35) DEFAULT NULL,
    FOREIGN KEY (`donation_purpose_id`) REFERENCES `donation_purpose`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `church_contact`;
CREATE TABLE `church_contact` (
    `contact_id` INT PRIMARY KEY AUTO_INCREMENT,
    `contact_full_name` VARCHAR(255) DEFAULT NULL,
    `contact_phone` VARCHAR(50) DEFAULT NULL,
    `contact_email` VARCHAR(255) DEFAULT NULL,
    `contact_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `email_subscription`;
CREATE TABLE `email_subscription` (
    `subscription_id` INT PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `contact_us`;
CREATE TABLE `contact_us` (
    `contact_us_id` INT PRIMARY KEY AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `church`;
CREATE TABLE `church` (
    `church_id` INT PRIMARY KEY AUTO_INCREMENT,
    `church_name` VARCHAR(255) NOT NULL,
    `church_description` TEXT DEFAULT NULL,
    `church_logo` VARCHAR(255) DEFAULT NULL,
    `public_id` VARCHAR(255),
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
    `bank_info` TEXT DEFAULT NULL,
    FOREIGN KEY (`address_id`) REFERENCES `address`(`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `church_banner`;
CREATE TABLE `church_banner` (
	`church_banner_id` INT PRIMARY KEY AUTO_INCREMENT,
    `church_banner` VARCHAR(255) NOT NULL,
    `public_id` VARCHAR(255),
    `church_id` INT NOT NULL,
    FOREIGN KEY (`church_id`) REFERENCES `church`(`church_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `church_staff`;
CREATE TABLE `church_staff` (
    `church_staff_id` INT PRIMARY KEY AUTO_INCREMENT,
    `church_staff_title` VARCHAR(255) NOT NULL,
    `church_staff_description` TEXT DEFAULT NULL,
    `church_id` INT NOT NULL,
    `user_id` INT DEFAULT NULL,
    FOREIGN KEY (`church_id`) REFERENCES `church`(`church_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- DROP TABLE IF EXISTS `service_type`;
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
    `service_id` INT PRIMARY KEY AUTO_INCREMENT,
    `service_name` VARCHAR(255) NOT NULL,
    `service_description` TEXT DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

USE `church_website`;
DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings`(
	`setting_id` INT PRIMARY KEY AUTO_INCREMENT,
    `setting_name` VARCHAR(100) NOT NULL UNIQUE,
    `setting_description` TEXT DEFAULT NULL,
    `setting_value_int` INT DEFAULT 0 ,
    `setting_value_char` VARCHAR(255) DEFAULT NULL,
    `setting_value_double` DECIMAL(10, 2) DEFAULT 0.0
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char, setting_value_double) VALUES (1, 'DEFAULT_PAGE_SIZE', 'The number of items listed in a page', 8, null, 0.0);
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char, setting_value_double) VALUES (2, 'LOCALE_LANGUAGE_CODE', 'Local language code', 0, 'en', 0.0);
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char, setting_value_double) VALUES (3, 'LOCALE_COUNTRY_CODE', 'Local country code', 0, 'GB', 0.0);
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char, setting_value_double) VALUES (4, 'CART_COOKIE_LIFETIME', 'Lifetime of cookie', 7*24*60*60, null, 0.0); -- 7 days
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char, setting_value_double) VALUES (5, 'TAX_RATE_FIXED', 'Fixed tax rate', 0, null, 0.0);
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char, setting_value_double) VALUES (6, 'TAX_RATE_PERCENT', 'Tax rate in percent', 0, null, 0.0);
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char, setting_value_double) VALUES (7, 'PASSWORD_RESET_TOKEN_LIFETIME', 'Password reset token expiry life time in minutes', 15, null, 0.0);
INSERT INTO settings(setting_id, setting_name, setting_description, setting_value_int, setting_value_char, setting_value_double) VALUES (8, 'TINY_MCE_KEY', 'Tiny mce api key', 0, 'kg3ajfr10laxj5741o0yzl05al2f24sd5ois0uw4nvumzt7i', 0.0);

DROP TABLE IF EXISTS `baptisim`;
CREATE TABLE `baptisim`(
	`request_id` INT PRIMARY KEY AUTO_INCREMENT,
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
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `holy_matrimony`;
CREATE TABLE `holy_matrimony`(
	`request_id` INT PRIMARY KEY AUTO_INCREMENT,
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
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `remembrance_prayer`;
CREATE TABLE `remembrance_prayer`(
	`request_id` INT PRIMARY KEY AUTO_INCREMENT,
	`requestor_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `christian_name_of_the_prayer_is_for` VARCHAR(100) NOT NULL UNIQUE,
    `spiritual_father_fullname` VARCHAR(100) NOT NULL UNIQUE,
    `prayer_for_date` DATE DEFAULT NULL,
    `request_date` DATETIME DEFAULT NULL,
    `message` TEXT DEFAULT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============ GATEWAY MANAGEMENT TABLE ================
DROP TABLE IF EXISTS `payment_gateway`;
CREATE TABLE `payment_gateway` (
    `payment_gateway_id` INT AUTO_INCREMENT PRIMARY KEY,
    `gateway_name` VARCHAR(255) NOT NULL,
    `api_key` TEXT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============== SHOPPING TABLES ======================

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
    `product_category_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `parent_category` INT DEFAULT NULL,
    FOREIGN KEY (`parent_category`) REFERENCES `product_category`(`product_category_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    `product_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `price` DECIMAL(10, 2) NOT NULL,
    `stock_quantity` INT NOT NULL,
    `delivery_type` ENUM('COLLECT', 'DELIVERY', 'DELIVERY_OR_COLLECT') DEFAULT 'COLLECT',
    `category_id` INT,
    `listing_status` ENUM('LISTED', 'NOT_LISTED') DEFAULT 'LISTED',
    `weight_in_kg` DECIMAL(10,2) DEFAULT 1.00,
    `version` BIGINT UNSIGNED,
    FOREIGN KEY (`category_id`) REFERENCES `product_category`(`product_category_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image` (
    `product_image_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `image_url` TEXT NOT NULL,
	`public_id` VARCHAR(255),
    `image_type` ENUM('THUMBNAIL', 'GALLERY') DEFAULT 'GALLERY',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
    `inventory_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `stock_quantity` INT NOT NULL,
    `last_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
    `cart_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL UNIQUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item` (
    `cart_item_id` INT AUTO_INCREMENT PRIMARY KEY,
    `cart_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `delivery_type` ENUM('DELIVERY', 'COLLECTION') DEFAULT 'COLLECTION',
    `quantity` INT NOT NULL,
    CONSTRAINT `unique_cart_product` UNIQUE (`product_id`, `cart_id`),
    FOREIGN KEY (`cart_id`) REFERENCES `cart`(`cart_id`) ON DELETE CASCADE,
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`) ON DELETE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

USE church_website;
DROP TABLE IF EXISTS `shipping`;
CREATE TABLE `shipping` (
    `shipping_id` INT AUTO_INCREMENT PRIMARY KEY,
   -- `order_id` INT NOT NULL, -- shipping must be per item (not per order) as different items may be shipped in different date/time
    `address_id` INT NOT NULL,
    `status` ENUM('PENDING', 'SHIPPED', 'COLLECTED', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
	`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `shipped_at` TIMESTAMP,
    `delivered_at` TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (`address_id`) REFERENCES `address`(`address_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
    `order_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT,
    `total_price` DECIMAL(10, 2) DEFAULT 0.0,
    `shipping_id` INT NOT NULL,
    `shipping_price` DECIMAL(10, 2) DEFAULT 0.0,
    `tax` DECIMAL(10, 2) DEFAULT 0.0,
    `total_quantity` INT DEFAULT 0,
    -- `delivery_mode` ENUM('DELIVERY', 'COLLECTION') DEFAULT 'COLLECTION',
    `status` ENUM('SUBMITTED', 'COMPLETED', 'CANCELLED') DEFAULT 'SUBMITTED',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
    FOREIGN KEY (`shipping_id`) REFERENCES `shipping`(`shipping_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
    `order_item_id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `quantity` INT NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `total_price` DECIMAL(10, 2) NOT NULL,
    `delivery_type` ENUM('DELIVERY', 'COLLECTION') DEFAULT 'COLLECTION',
    `status` ENUM('SUBMITTED', 'COMPLETED', 'CANCELLED') DEFAULT 'SUBMITTED',
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`) ON DELETE CASCADE,
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `order_payment`;
CREATE TABLE `order_payment` (
    `order_payment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `amount` DECIMAL(10, 2) NOT NULL,
    `payment_method` VARCHAR(255) NOT NULL,
    `status` ENUM('PENDING', 'SUCCESS', 'FAILED') DEFAULT 'PENDING',
    `session_url` TEXT DEFAULT NULL,
    `session_id` VARCHAR(255) DEFAULT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `product_review`;
CREATE TABLE `product_review` (
    `product_review_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `rating` INT CHECK (`rating` BETWEEN 1 AND 5),
    `comment` TEXT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `shipping_plan`;
CREATE TABLE `shipping_plan`(
	`shipping_plan_id`  INT AUTO_INCREMENT PRIMARY KEY,
    `plan_name` VARCHAR(100) NOT NULL,
    `base_price` DECIMAL(10, 2) DEFAULT 0.0,
    `per_mile_price` DECIMAL(10, 2) DEFAULT 0.0,
    `per_kg_price` DECIMAL(10, 2) DEFAULT 0.0
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO shipping_plan(shipping_plan_id, plan_name, base_price, per_mile_price, per_kg_price) VALUES (1, 'DOMESTIC_PLAN', 0.0, 0.0, 0.0);
INSERT INTO shipping_plan(shipping_plan_id, plan_name, base_price, per_mile_price, per_kg_price) VALUES (2, 'INTERNATIONAL_PLAN', 0.0, 0.0, 0.0);

-- =================================================================

DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`(
	`id`  INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `content` MEDIUMTEXT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `is_archived` BOOLEAN DEFAULT false,
    `is_active` BOOLEAN DEFAULT true,
    `is_featured` BOOLEAN DEFAULT false
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `about_us`;
CREATE TABLE `about_us`(
	`id`  INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `content` LONGTEXT NOT NULL,
    `include_title_in_public_view` BOOLEAN DEFAULT true,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `password_reset_token`;
CREATE TABLE `password_reset_token`(
	`id`  INT AUTO_INCREMENT PRIMARY KEY,
    `token` VARCHAR(255) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `is_used` BOOLEAN DEFAULT false,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `expire_at` DATETIME NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `message_reply`;
CREATE TABLE `message_reply`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `receiver_email` VARCHAR(100) NOT NULL,
	`contact_us_id` INT NOT NULL,
    `subject` VARCHAR(255) NOT NULL,
    `message` TEXT NOT NULL,
    `is_follow_up` BOOLEAN DEFAULT false,
    `sent_at` DATETIME NOT NULL,
    FOREIGN KEY(`contact_us_id`) REFERENCES `contact_us`(`contact_us_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
