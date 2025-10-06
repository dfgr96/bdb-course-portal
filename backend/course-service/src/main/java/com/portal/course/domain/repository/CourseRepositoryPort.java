package com.portal.course.domain.repository;

import com.portal.dto.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepositoryPort {

    Course save(Course course);
    List<Course> findAll();
    Optional<Course> findById(Long id);
}
