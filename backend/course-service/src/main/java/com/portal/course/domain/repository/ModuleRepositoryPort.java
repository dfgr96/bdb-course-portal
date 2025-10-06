package com.portal.course.domain.repository;

import com.portal.dto.Section;

import java.util.List;

public interface ModuleRepositoryPort {

    Section save(Long courseId, Section section);
    List<Section> findByCourseId(Long courseId);
}
