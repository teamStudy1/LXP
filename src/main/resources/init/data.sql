INSERT INTO Category (name, parent_id) VALUES
                                           ('개발', NULL),
                                           ('디자인', NULL),
                                           ('비즈니스', NULL),
                                           ('마케팅', NULL),
                                           ('프론트엔드', 1),
                                           ('백엔드', 1),
                                           ('모바일', 1),
                                           ('UI/UX', 2),
                                           ('그래픽 디자인', 2),
                                           ('경영', 3),
                                           ('재무', 3),
                                           ('디지털 마케팅', 4),
                                           ('콘텐츠 마케팅', 4);


INSERT INTO User (email, password, name, active_status, role) VALUES
                                                                     ('admin@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '관리자', 'ACTIVE', 'ADMIN'),
                                                                     ('john.doe@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', 'John', 'ACTIVE', 'INSTRUCTOR'),
                                                                     ('jane.smith@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', 'Jane', 'ACTIVE', 'INSTRUCTOR'),
                                                                     ('mike.wilson@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', 'Mike', 'ACTIVE', 'INSTRUCTOR'),
                                                                     ('sarah.lee@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', 'Sarah', 'ACTIVE', 'INSTRUCTOR'),
                                                                     ('student1@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '학생1', 'ACTIVE', 'STUDENT'),
                                                                     ('student2@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '학생2', 'ACTIVE', 'STUDENT'),
                                                                     ('student3@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '학생3', 'ACTIVE', 'STUDENT'),
                                                                     ('student4@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '학생4', 'ACTIVE', 'STUDENT'),
                                                                     ('student5@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '학생5', 'ACTIVE', 'STUDENT'),
                                                                     ('student6@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '학생6', 'DEACTIVE', 'STUDENT'),
                                                                     ('robert.kim@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', 'Robert', 'ACTIVE', 'INSTRUCTOR'),
                                                                     ('emily.park@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', 'Emily', 'ACTIVE', 'INSTRUCTOR'),
                                                                     ('student7@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '학생7', 'ACTIVE', 'STUDENT'),
                                                                     ('student8@example.com', '$2a$10$abcdefghijklmnopqrstuvwxyz', '학생8', 'ACTIVE', 'STUDENT');



INSERT INTO User_Profile(user_id, introduction, resume) VALUES
                                                            (2, '10년 경력의 풀스택 개발자입니다.', 'Google, Amazon 근무 경력'),
                                                            (3, 'UI/UX 전문 디자이너입니다.', 'Apple, Microsoft 디자인 팀 출신'),
                                                            (4, '백엔드 아키텍처 전문가입니다.', 'Netflix, Uber 시니어 엔지니어'),
                                                            (5, '프론트엔드 강사로 5년째 활동중입니다.', 'Udemy 베스트셀러 강사'),
                                                            (6, '웹 개발을 배우고 있는 학생입니다.', NULL),
                                                            (7, '디자인에 관심이 많은 학생입니다.', NULL),
                                                            (12, 'Java 전문 강사입니다.', 'Oracle 공인 자바 전문가'),
                                                            (13, 'Python과 데이터 사이언스 전문가입니다.', 'MIT 박사 출신');



INSERT INTO Course (title, instructor_id, category_id, total_time, total_lecture_count) VALUES
                                                                                            ('React 완벽 마스터', 2, 13,5, 150),
                                                                                            ('Vue.js 시작하기', 2, 12,8.0, 120),
                                                                                            ('Java 스프링 부트', 12, 6, 40.5, 200),
                                                                                            ('Python 데이터 분석', 13, 11,30.0, 180),
                                                                                            ('UI/UX 디자인 기초', 3, 8, 15.5, 90),
                                                                                            ('Figma 실전 프로젝트', 3, 9,12.0, 80),
                                                                                            ('Node.js 백엔드 개발', 4, 6, 35.0, 190),
                                                                                            ('안드로이드 앱 개발', 5, 12,28.0, 160),
                                                                                            ('iOS 스위프트 프로그래밍', 5, 11,32.0, 170),
                                                                                            ('풀스택 웹 개발 부트캠프', 2, 1, 80.0, 400),
                                                                                            ('디지털 마케팅 전략', 4, 12, 20.0, 100),
                                                                                            ('React Native 모바일 앱', 2, 7, 25.0, 140),
                                                                                            ('Python Django 웹 프레임워크', 13, 2,8.0, 195),
                                                                                            ('TypeScript 마스터클래스', 12, 5, 22.0, 130),
                                                                                            ('그래픽 디자인 with 포토샵', 3, 9, 18.5, 95);


INSERT INTO Course_Detail(course_id, content, content_detail) VALUES
                                                                  (1, 'React의 기초부터 고급까지 모든 것을 배웁니다.', 'Hooks, Context API, Redux, Next.js까지 포함된 완벽한 React 코스입니다.'),
                                                                  (2, 'Vue.js 3의 Composition API를 활용한 모던 웹 개발', 'Vue Router, Vuex, Pinia를 활용한 실전 프로젝트'),
                                                                  (3, '스프링 부트로 RESTful API 서버 구축하기', 'JPA, Security, JWT 인증, 테스트 코드 작성까지'),
                                                                  (4, 'Pandas, NumPy, Matplotlib을 활용한 데이터 분석', '실제 데이터셋을 활용한 프로젝트 중심 학습'),
                                                                  (5, '사용자 중심의 디자인 사고방식 배우기', '와이어프레임부터 프로토타입까지 전 과정'),
                                                                  (6, 'Figma를 활용한 실무 디자인 프로젝트', '협업 도구 활용과 디자인 시스템 구축'),
                                                                  (7, 'Express.js로 확장 가능한 서버 만들기', 'MongoDB, PostgreSQL 연동 및 배포'),
                                                                  (8, 'Kotlin으로 안드로이드 앱 만들기', 'Material Design과 최신 안드로이드 기술'),
                                                                  (9, 'SwiftUI를 활용한 iOS 앱 개발', 'App Store 출시까지의 전 과정'),
                                                                  (10, '프론트엔드부터 백엔드까지 완성하기', 'MERN 스택을 활용한 풀스택 개발'),
                                                                  (11, 'SEO, SNS 마케팅, 구글 애널리틱스', '실전 마케팅 캠페인 기획 및 운영'),
                                                                  (12, 'React Native로 iOS/Android 동시 개발', '네이티브 모듈 연동과 앱 스토어 배포'),
                                                                  (13, 'Django REST Framework로 API 서버 구축', 'Celery, Redis를 활용한 비동기 처리'),
                                                                  (14, 'JavaScript의 타입 안정성 확보하기', '제네릭, 데코레이터 등 고급 TypeScript'),
                                                                  (15, '포토샵 기초부터 실무 디자인까지', '로고, 포스터, 배너 디자인 프로젝트');



INSERT INTO Section (course_id, name, order_num) VALUES
-- Course 1: React 완벽 마스터
(1, 'React 기초', 1),
(1, 'React Hooks', 2),
(1, '상태 관리', 3),
(1, 'Next.js', 4),
-- Course 2: Vue.js 시작하기
(2, 'Vue.js 소개', 1),
(2, 'Composition API', 2),
(2, 'Vue Router', 3),
-- Course 3: Java 스프링 부트
(3, '스프링 부트 시작하기', 1),
(3, 'JPA와 데이터베이스', 2),
(3, 'RESTful API 설계', 3),
(3, '보안과 인증', 4),
-- Course 4: Python 데이터 분석
(4, 'Python 기초', 1),
(4, 'NumPy와 Pandas', 2),
(4, '데이터 시각화', 3),
-- Course 5: UI/UX 디자인 기초
(5, '디자인 사고', 1),
(5, '사용자 리서치', 2),
(5, '와이어프레임과 프로토타입', 3);


INSERT INTO Lecture (section_id, name, lecture_time, order_num, video_url) VALUES
-- Section 1: React 기초
(1, 'React란 무엇인가?', '1600', 1, 'https://video.example.com/react-intro'),
(1, 'JSX 문법 이해하기', '4320', 2, 'https://video.example.com/jsx-syntax'),
(1, '컴포넌트와 Props', '7140', 3, 'https://video.example.com/components'),
(1, 'State와 생명주기', '3720', 4, 'https://video.example.com/state'),
-- Section 2: React Hooks
(2, 'useState Hook', '1040', 1, 'https://video.example.com/usestate'),
(2, 'useEffect Hook', '3110', 2, 'https://video.example.com/useeffect'),
(2, 'useContext Hook', '5020', 3, 'https://video.example.com/usecontext'),
(2, 'Custom Hooks', '3040', 4, 'https://video.example.com/custom-hooks'),
-- Section 3: 상태 관리
(3, 'Context API', '2040', 1, 'https://video.example.com/context-api'),
(3, 'Redux 기초', '1200', 2, 'https://video.example.com/redux-basics'),
(3, 'Redux Toolkit', '1090', 3, 'https://video.example.com/redux-toolkit'),
-- Section 5: Vue.js 소개
(5, 'Vue.js 설치와 설정', '4090', 1, 'https://video.example.com/vue-setup'),
(5, '템플릿 문법', '1200', 2, 'https://video.example.com/vue-template'),
(5, '반응형 데이터', '3200', 3, 'https://video.example.com/vue-reactive'),
-- Section 9: 스프링 부트 시작하기
(9, '스프링 부트 프로젝트 생성', '3120', 1, 'https://video.example.com/spring-setup'),
(9, 'IoC와 DI 이해하기', '4020', 2, 'https://video.example.com/spring-ioc'),
(9, '스프링 MVC', '1020', 3, 'https://video.example.com/spring-mvc'),
-- Section 13: Python 기초
(13, 'Python 설치와 환경설정', '1000', 1, 'https://video.example.com/python-setup'),
(13, '변수와 자료형', '2000', 2, 'https://video.example.com/python-types'),
(13, '제어문과 반복문', '1440', 3, 'https://video.example.com/python-control');

-- Tag 더미 데이터
INSERT INTO Tag (name) VALUES
                           ('초급'),
                           ('중급'),
                           ('고급'),
                           ('웹개발'),
                           ('모바일'),
                           ('프론트엔드'),
                           ('백엔드'),
                           ('풀스택'),
                           ('디자인'),
                           ('데이터'),
                           ('JavaScript'),
                           ('Python'),
                           ('Java'),
                           ('베스트셀러'),
                           ('추천');

-- CourseTag 더미 데이터
INSERT INTO Course_Tag(course_id, tag_id) VALUES
-- Course 1: React 완벽 마스터
(1, 2), (1, 4), (1, 6), (1, 11), (1, 14),
-- Course 2: Vue.js 시작하기
(2, 1), (2, 4), (2, 6), (2, 11),
-- Course 3: Java 스프링 부트
(3, 2), (3, 4), (3, 7), (3, 13), (3, 14),
-- Course 4: Python 데이터 분석
(4, 2), (4, 10), (4, 12), (4, 15),
-- Course 5: UI/UX 디자인 기초
(5, 1), (5, 9), (5, 15),
-- Course 6: Figma 실전 프로젝트
(6, 2), (6, 9),
-- Course 7: Node.js 백엔드 개발
(7, 2), (7, 4), (7, 7), (7, 11),
-- Course 8: 안드로이드 앱 개발
(8, 2), (8, 5), (8, 13),
-- Course 9: iOS 스위프트 프로그래밍
(9, 2), (9, 5),
-- Course 10: 풀스택 웹 개발 부트캠프
(10, 3), (10, 4), (10, 8), (10, 14), (10, 15);

-- Enrollment 더미 데이터
INSERT INTO Enrollment (user_id, course_id) VALUES
-- 학생1 (user_id: 6)
(6, 1), (6, 2), (6, 10),
-- 학생2 (user_id: 7)
(7, 5), (7, 6), (7, 15),
-- 학생3 (user_id: 8)
(8, 3), (8, 7), (8, 13),
-- 학생4 (user_id: 9)
(9, 4), (9, 10), (9, 13),
-- 학생5 (user_id: 10)
(10, 1), (10, 3), (10, 4), (10, 7),
-- 학생7 (user_id: 14)
(14, 8), (14, 9), (14, 12),
-- 학생8 (user_id: 15)
(15, 1), (15, 2), (15, 3), (15, 5), (15, 10);