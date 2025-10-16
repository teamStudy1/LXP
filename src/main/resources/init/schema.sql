



drop table if exists course_tag;
drop table if exists tag;
drop table if exists enrollment;
drop table if exists lecture;
drop table if exists section;
drop table if exists course_detail;
drop table if exists course;
drop table if exists user_profile;
drop table if exists user;
drop table if exists category;





CREATE TABLE IF NOT EXISTS Category (
                          category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(100) NOT NULL,
                          parent_id BIGINT,
                          depth INT NOT NULL DEFAULT 0,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (parent_id) REFERENCES Category(category_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS User (
                      user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      nickname VARCHAR(50) NOT NULL,
                      active_status ENUM('ACTIVE', 'DEACTIVE') NOT NULL DEFAULT 'ACTIVE',
                      role ENUM('STUDENT', 'INSTRUCTOR', 'ADMIN') NOT NULL DEFAULT 'STUDENT',
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS User_Profile (
                                            user_profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                            user_id BIGINT NOT NULL UNIQUE,
                                            introduction TEXT,
                                            resume TEXT,
                                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                            FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS Course (
                        course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        title VARCHAR(255) NOT NULL,
                        instructor_id BIGINT NOT NULL,
                        category_id BIGINT,
                        total_time INT DEFAULT 0,
                        total_lecture_count INT DEFAULT 0,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        FOREIGN KEY (instructor_id) REFERENCES User(user_id) ON DELETE CASCADE,
                        FOREIGN KEY (category_id) REFERENCES Category(category_id) ON DELETE SET NULL
);



CREATE TABLE IF NOT EXISTS Course_Detail(
                              course_detail_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              course_id BIGINT NOT NULL UNIQUE,
                              content TEXT,
                              content_detail TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Section (
                         section_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         course_id BIGINT NOT NULL,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         order_num INT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Lecture (
                         lecture_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         section_id BIGINT NOT NULL,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         lecture_time INT,
                         order_num INT NOT NULL,
                         video_url VARCHAR(500),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (section_id) REFERENCES Section(section_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Tag (
                     tag_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(50) NOT NULL UNIQUE,
                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Enrollment (
                            user_id BIGINT NOT NULL,
                            course_id BIGINT NOT NULL,
                            enrollment_status ENUM('ACTIVE', 'DEACTIVE') NOT NULL DEFAULT 'ACTIVE',
                            enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (user_id, course_id),
                            FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE,
                            FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Course_Tag(
                           course_id BIGINT NOT NULL,
                           tag_id BIGINT NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (course_id, tag_id),
                           FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE,
                           FOREIGN KEY (tag_id) REFERENCES Tag(tag_id) ON DELETE CASCADE
);

