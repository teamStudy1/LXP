package com.lxp.infrastructure.dao.course.repository;

import com.lxp.domain.course.Course;
import com.lxp.domain.course.CourseRepository;
import com.lxp.infrastructure.dao.course.CourseDao;
import com.lxp.infrastructure.dao.course.LectureDao;
import com.lxp.infrastructure.dao.course.SectionDao;
import com.lxp.infrastructure.dao.course.TagDao;
import com.lxp.infrastructure.mapper.CourseMapper;
import com.lxp.infrastructure.row.course.LectureRow;
import com.lxp.infrastructure.row.course.SectionRow;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

public class JdbcCourseRepository implements CourseRepository {
    private final CourseDao courseDao;
    private final SectionDao sectionDao;
    private final LectureDao lectureDao;
    private final TagDao tagDao;

    public JdbcCourseRepository(
            CourseDao courseDao,
            SectionDao sectionDao,
            LectureDao lectureDao,
            TagDao tagDao
    ) {
        this.courseDao = courseDao;
        this.sectionDao = sectionDao;
        this.lectureDao = lectureDao;
        this.tagDao = tagDao;
    }


    @Override
    public Optional<Course> findAggregateById(Long courseId) {
        try {
            var courseRowOpt = courseDao.findById(courseId);
            if (courseRowOpt.isEmpty()) return Optional.empty();

            var sectionRows = sectionDao.findAllByCourseId(courseId);

            var sectionIds = sectionRows.stream()
                    .map(SectionRow::sectionId)
                    .toList();

            var lectureRows = sectionIds.isEmpty()
                    ? Collections.<LectureRow>emptyList()
                    : lectureDao.findAllBySectionIds(sectionIds);

            var tagRows = tagDao.findAllByCourseId(courseId);

            var course = CourseMapper.toDomain(
                    courseRowOpt.get(),
                    sectionRows,
                    lectureRows,
                    tagRows
            );
            return Optional.of(course);
        } catch (SQLException e) {
            throw new RuntimeException("Load Course aggregate failed. id=" + courseId, e);
        }
    }
}
