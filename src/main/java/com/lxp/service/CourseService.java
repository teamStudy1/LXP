package com.lxp.service;

import com.lxp.api.dto.CourseView;
import com.lxp.config.TransactionManager;
import com.lxp.domain.course.Course;
import com.lxp.infrastructure.dao.CourseDao;
import com.lxp.infrastructure.dao.CourseDetailDao;
import com.lxp.infrastructure.mapper.CourseMapper;
import com.lxp.infrastructure.row.course.CourseRow;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {
    private final CourseDao courseDao;
    private final CourseDetailDao courseDetailDao;

    public CourseService(CourseDao courseDao, CourseDetailDao courseDetailDao) {
        this.courseDao = courseDao;
        this.courseDetailDao = courseDetailDao;
    }

    public Course createCourse(String title, Long instructorId, double totalTime, int totalLectureCount, String content, String contentDetail) {
        Course newCourse = new Course(title, instructorId, totalTime, totalLectureCount, content, contentDetail, LocalDateTime.now());

        try {
            TransactionManager.beginTransaction();

            CourseRow savedCourseRow = courseDao.save(newCourse);

            courseDetailDao.save(savedCourseRow.courseId(), newCourse.getContent(), newCourse.getContentDetail());

            TransactionManager.commit();

            return CourseMapper.toDomain(savedCourseRow);

        } catch (SQLException e) {
            System.err.println("강좌 생성 중 데이터베이스 오류 발생: " + e.getMessage());

            TransactionManager.rollback();

            throw new RuntimeException("강좌 생성에 실패했습니다. 데이터베이스 작업을 롤백합니다.", e);
        }
    }

    public CourseView getCourseById(Long id) throws SQLException {
        Course course = courseDao.findById(id)
                .map(CourseMapper::toDomain)
                .orElseThrow(() -> new IllegalStateException("ID가 " + id + "인 강좌를 찾을 수 없습니다."));
        return CourseMapper.toView(course);
    }

    public List<CourseView> searchCoursesByTitle(String keyword) throws SQLException {
        return courseDao.findByTitleContaining(keyword).stream()
                .map(CourseMapper::toDomain)
                .map(CourseMapper::toView)
                .collect(Collectors.toList());
    }

    public List<CourseView> getAllCourses() throws SQLException {
        return courseDao.findAll().stream()
                .map(CourseMapper::toDomain)
                .map(CourseMapper::toView)
                .collect(Collectors.toList());
    }

    public boolean updateCourseTitle(Long id, String newTitle) throws SQLException {
        int updatedRows = courseDao.updateTitle(id, newTitle);
        if (updatedRows == 0) {
            throw new IllegalStateException("수정할 강좌(ID: " + id + ")가 존재하지 않습니다.");
        }
        return updatedRows > 0;
    }
}