package com.portal.course.application;

import com.portal.dto.Course;
import com.portal.dto.Section;
import com.portal.course.domain.repository.CourseRepositoryPort;
import com.portal.course.domain.repository.ModuleRepositoryPort;
import com.portal.dto.ViewCourses;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepositoryPort courseRepository;
    private final ModuleRepositoryPort moduleRepository;

    public CourseService(CourseRepositoryPort courseRepository, ModuleRepositoryPort moduleRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
    }

    public ViewCourses getAllCourses(Long userId) {
        return new ViewCourses(courseRepository.findAvailableAll(userId), courseRepository.findActiveAll(userId));
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Section addModule(Long courseId, Section module) {
        return moduleRepository.save(courseId, module);
    }
}
