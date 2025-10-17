package com.lxp.service;

import com.lxp.api.dto.CourseView;
import com.lxp.domain.course.Course;
import com.lxp.infrastructure.dao.CourseDao;
import com.lxp.infrastructure.mapper.CourseMapper;
import com.lxp.infrastructure.row.course.CourseRow;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {
    private final CourseDao courseDao;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Course createCourse(String title, Long instructorId) throws SQLException {
        Course newCourse = new Course(title, instructorId, null, null, null);
        CourseRow savedRow = courseDao.save(newCourse);
        return CourseMapper.toDomain(savedRow);
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
        courseDao.findById(id).orElseThrow(() -> new IllegalStateException("수정할 강좌(ID: " + id + ")가 존재하지 않습니다."));
        int updatedRows = courseDao.updateTitle(id, newTitle);
        return updatedRows > 0;
    }

}