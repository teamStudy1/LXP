package com.lxp.course.persistence;

import com.lxp.course.domain.model.Course;
import com.lxp.course.domain.repository.CourseRepository;
import com.lxp.course.persistence.dao.CourseDao;
import com.lxp.course.persistence.dao.LectureDao;
import com.lxp.course.persistence.dao.SectionDao;
import com.lxp.course.persistence.dao.TagDao;
import com.lxp.course.persistence.mapper.CourseMapper;
import com.lxp.course.persistence.row.LectureRow;
import com.lxp.course.persistence.row.SectionRow;

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
