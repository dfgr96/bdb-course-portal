package com.portal.course.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    @Query(value = "SELECT c.* FROM courses c JOIN enrollments e ON c.id = e.course_id WHERE e.user_id = :userId",
            nativeQuery = true)
    List<CourseEntity> findActiveCoursesByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT c.* FROM courses c LEFT JOIN enrollments e ON c.id = e.course_id AND e.user_id = :userId WHERE e.id IS NULL",
            nativeQuery = true)
    List<CourseEntity> findCoursesNotEnrolledByUserId(@Param("userId") Long userId);
}
