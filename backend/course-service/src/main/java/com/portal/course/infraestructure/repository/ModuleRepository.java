package com.portal.course.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<SectionEntity, Long> {
    List<SectionEntity> findByCourseIdOrderByOrderIndex(Long courseId);
}
