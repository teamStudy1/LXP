package com.lxp.service;

import com.lxp.api.dto.CourseView;
import com.lxp.config.TransactionManager;
import com.lxp.domain.course.Course;
import com.lxp.domain.course.Tag;
import com.lxp.infrastructure.dao.*;
import com.lxp.infrastructure.mapper.CourseMapper;
import com.lxp.infrastructure.row.course.CourseRow;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseService {
    private final CourseDao courseDao;
    private final CourseDetailDao courseDetailDao;
    private final TagDao tagDao;
    private final CourseTagDao courseTagDao;

    public CourseService(CourseDao courseDao, CourseDetailDao courseDetailDao, TagDao tagDao, CourseTagDao courseTagDao) {
        this.courseDao = courseDao;
        this.courseDetailDao = courseDetailDao;
        this.tagDao = tagDao;
        this.courseTagDao = courseTagDao;
    }

    public Course createCourse(String title, Long instructorId, double totalTime, int totalLectureCount, String content, String contentDetail, Set<String> tagNames) {
        Course newCourse = new Course(title, instructorId, totalTime, totalLectureCount, content, contentDetail, LocalDateTime.now());

        try {
            TransactionManager.beginTransaction();

            CourseRow savedCourseRow = courseDao.save(newCourse);
            long newCourseId = savedCourseRow.courseId();

            courseDetailDao.save(newCourseId, newCourse.getContent(), newCourse.getContentDetail());

            for (String tagName : tagNames) {
                Optional<Tag> existingTag = tagDao.findByName(tagName.trim());

                Tag tagToLink = existingTag.orElseGet(() -> {
                    try {
                        return tagDao.save(tagName.trim());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                courseTagDao.save(newCourseId, tagToLink.getId());
            }

            TransactionManager.commit();
            return CourseMapper.toDomain(savedCourseRow);

        } catch (Exception e) {
            System.err.println("강좌 생성 중 오류 발생: " + e.getMessage());
            TransactionManager.rollback();
            throw new RuntimeException("강좌 생성에 실패했습니다.", e);
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