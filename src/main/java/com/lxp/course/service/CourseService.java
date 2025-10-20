package com.lxp.course.service;

import com.lxp.category.domain.Category;
import com.lxp.category.domain.CategoryRepository;
import com.lxp.config.TransactionManager;
import com.lxp.course.domain.model.Course;
import com.lxp.course.domain.model.CourseDetail;
import com.lxp.course.domain.model.Section;
import com.lxp.course.domain.model.Tag;
import com.lxp.course.domain.repository.CourseRepository;
import com.lxp.course.web.dto.request.CourseRequest;
import com.lxp.course.web.dto.request.CourseUpdateRequest;
import com.lxp.course.web.dto.request.SectionRequest;
import com.lxp.course.web.dto.response.CourseAllResponse;
import com.lxp.course.web.dto.response.CourseResponse;
import com.lxp.user.domain.model.User;
import com.lxp.user.domain.model.enums.ActiveStatus;
import com.lxp.user.domain.model.enums.UserRole;
import com.lxp.user.domain.repository.UserRespository;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO("")
public class CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRespository userRespository;

    public CourseService(
            CourseRepository courseRepository,
            CategoryRepository categoryRepository,
            UserRespository userRespository) {

        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.userRespository = userRespository;
    }

    public void save(CourseRequest courseRequest) throws SQLException {

        try {
            Category category =
                    categoryRepository
                            .findById(courseRequest.categoryId())
                            .orElseThrow(() -> new SQLException("Category not found"));

            User user =
                    userRespository
                            .findUserById(courseRequest.instructorId())
                            .orElseThrow(() -> new SQLException("User not found"));

            if (user.getRole() != UserRole.INSTRUCTOR && user.getActiveStatus() != ActiveStatus.ACTIVE) {
                throw new SQLException("Instructor role not allowed.");
            }

            TransactionManager.beginTransaction();

            Set<Tag> tags = courseRepository.findOrCreateByNames(courseRequest.tagNames());

            List<SectionRequest> sectionRequests = courseRequest.sections();
            List<Section> sections =
                    IntStream.range(0, sectionRequests.size())
                            .mapToObj(i -> Section.createFromRequest(sectionRequests.get(i), i + 1))
                            .collect(Collectors.toList());
            Course newCourse =
                    Course.create(
                            courseRequest.title(),
                            user.getId(),
                            courseRequest.content(),
                            courseRequest.contentDetail(),
                            category.getId(),
                            sections,
                            tags);

            courseRepository.save(newCourse);

            TransactionManager.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            TransactionManager.rollback();
            throw new RuntimeException("Course save failed", e);
        } finally {
            TransactionManager.close();
        }
    }

    public CourseResponse findById(Long courseId) throws SQLException {
        try {
            Course course =
                    courseRepository
                            .findAggregateById(courseId)
                            .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

            Category category = categoryRepository.findAllByParents(course.getCategoryId());

            User user =
                    userRespository
                            .findUserById(course.getInstructorId())
                            .orElseThrow(() -> new SQLException("User not found"));

            return CourseResponse.from(course, category, user);
        } catch (Exception e) {
            throw new RuntimeException("Course Detail failed", e);
        }
    }

    public List<CourseAllResponse> findAll() {
        try {
            List<Course> courses = courseRepository.findAll();

            return courses.stream()
                    .map(
                            course -> {
                                User user = userRespository.findUserById(course.getInstructorId()).orElse(null);
                                System.out.println(user.getName());
                                return CourseAllResponse.from(course, user);
                            })
                    .toList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Course Detail failed", e);
        }
    }

    public List<CourseAllResponse> searchCoursesByKeyword(String keyword) throws SQLException {
        List<Course> courses = courseRepository.findByTitleContaining(keyword);

        return courses.stream()
                .map(
                        course -> {
                            User user = userRespository.findUserById(course.getInstructorId()).orElse(null);
                            return CourseAllResponse.from(course, user);
                        })
                .toList();
    }

    public void updateCourse(CourseUpdateRequest request) throws SQLException {
        try {
            Course course =
                    courseRepository
                            .findById(request.courseId())
                            .orElseThrow(
                                    () -> new IllegalArgumentException("Course not found: " + request.courseId()));

            course.rename(request.title());
            course.setDetail(new CourseDetail(request.content(), request.contentDetail()));

            courseRepository.update(course);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            TransactionManager.close();
        }
    }
}
