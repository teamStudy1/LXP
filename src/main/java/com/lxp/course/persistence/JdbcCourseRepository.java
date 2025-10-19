package com.lxp.course.persistence;

import com.lxp.course.domain.model.Course;
import com.lxp.course.domain.model.Section;
import com.lxp.course.domain.model.Tag;
import com.lxp.course.domain.repository.CourseRepository;
import com.lxp.course.persistence.dao.CourseDao;
import com.lxp.course.persistence.dao.LectureDao;
import com.lxp.course.persistence.dao.SectionDao;
import com.lxp.course.persistence.dao.TagDao;
import com.lxp.course.persistence.mapper.CourseMapper;
import com.lxp.course.persistence.mapper.LectureMapper;
import com.lxp.course.persistence.mapper.SectionMapper;
import com.lxp.course.persistence.mapper.TagMapper;
import com.lxp.course.persistence.row.CourseRow;
import com.lxp.course.persistence.row.LectureRow;
import com.lxp.course.persistence.row.SectionRow;
import com.lxp.course.persistence.row.TagRow;
import com.lxp.exception.DataAccessException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class JdbcCourseRepository implements CourseRepository {
    private final CourseDao courseDao;
    private final SectionDao sectionDao;
    private final LectureDao lectureDao;
    private final TagDao tagDao;

    public JdbcCourseRepository(
            CourseDao courseDao, SectionDao sectionDao, LectureDao lectureDao, TagDao tagDao) {
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

            var sectionIds = sectionRows.stream().map(SectionRow::sectionId).toList();

            var lectureRows =
                    sectionIds.isEmpty()
                            ? Collections.<LectureRow>emptyList()
                            : lectureDao.findAllBySectionIds(sectionIds);

            var tagRows = tagDao.findAllByCourseId(courseId);

            var course = CourseMapper.toDomain(courseRowOpt.get(), sectionRows, lectureRows, tagRows);

            return Optional.of(course);
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw new RuntimeException("Load Course aggregate failed. id=" + courseId, e);
        }
    }

    @Override
    public Set<Tag> findOrCreateByNames(List<String> tagNames) {

        try {
            List<TagRow> existingTagRows = tagDao.findByNameIn(tagNames);
            List<Tag> existingTags = existingTagRows.stream().map(TagMapper::toDomain).toList();

            Set<String> existingTagNames =
                    existingTags.stream().map(Tag::getName).collect(Collectors.toSet());

            List<String> newTagNames =
                    tagNames.stream()
                            .filter(name -> !existingTagNames.contains(name))
                            .collect(Collectors.toList());

            List<Tag> newTags = new ArrayList<>();
            if (!newTagNames.isEmpty()) {
                List<TagRow> createdTagRows = tagDao.batchInsert(newTagNames);
                newTags = createdTagRows.stream().map(TagMapper::toDomain).toList();
            }

            Set<Tag> allTags = new HashSet<>(existingTags);
            allTags.addAll(newTags);

            return allTags;

        } catch (SQLException e) {
            throw new RuntimeException("Load Tag failed.");
        }
    }

    @Override
    public void save(Course course) {
        try {
            CourseRow courseRow = CourseMapper.toRow(course);
            Long courseId = courseDao.save(courseRow);

            List<Section> sections = course.getSections();
            if (!sections.isEmpty()) {

                List<SectionRow> sectionRows =
                        sections.stream()
                                .map(item -> SectionMapper.toRow(item, courseId))
                                .collect(Collectors.toList());

                List<Long> sectionIds = sectionDao.batchInsert(sectionRows);

                List<LectureRow> allLectureRows = new ArrayList<>();
                for (int i = 0; i < sections.size(); i++) {
                    Section section = sections.get(i);
                    Long sectionId = sectionIds.get(i);

                    List<LectureRow> lectureRows =
                            section.getLectures().stream()
                                    .map(lecture -> LectureMapper.toRow(lecture, sectionId))
                                    .toList();

                    allLectureRows.addAll(lectureRows);
                }

                if (!allLectureRows.isEmpty()) {
                    lectureDao.batchInsert(allLectureRows);
                }
            }

            Set<Tag> tags = course.getTags();
            if (!tags.isEmpty()) {
                tagDao.batchInsert(courseId, tags);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
            throw new RuntimeException("강좌 저장 중 데이터베이스 오류 발생", e);
        }
    }

    @Override
    public List<Course> findAll() {
        try {
            List<CourseRow> courseRows = courseDao.findAll();

            if (courseRows == null || courseRows.isEmpty()) {
                return Collections.emptyList();
            }
            return courseRows.stream()
                    .map(
                            courseRow ->
                                    CourseMapper.toDomain(
                                            courseRow,
                                            Collections.emptyList(),
                                            Collections.emptyList(),
                                            Collections.emptyList()))
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }
    }

    @Override
    public List<Course> findByTitleContaining(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return findAll();
        }

        List<CourseRow> courseRows = courseDao.findByTitleContaining(keyword);

        if (courseRows == null || courseRows.isEmpty()) {
            return Collections.emptyList();
        }

        return courseRows.stream()
                .map(
                        courseRow ->
                                CourseMapper.toDomain(
                                        courseRow,
                                        Collections.emptyList(),
                                        Collections.emptyList(),
                                        Collections.emptyList()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findById(Long courseId) {
        Optional<CourseRow> row = courseDao.findById(courseId);
        if (row.isPresent()) {
            return Optional.of(
                    CourseMapper.toDomain(
                            row.get(),
                            Collections.emptyList(),
                            Collections.emptyList(),
                            Collections.emptyList()));
        }
        return Optional.empty();
    }

    @Override
    public Course update(Course course) throws SQLException {
        courseDao.update(CourseMapper.toRow(course));
        return findAggregateById(course.getId()).orElse(null);
    }
}
