package com.portal.course.infraestructure.repository;

import com.portal.dto.Section;
import com.portal.course.domain.repository.ModuleRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleRepositoryAdapter implements ModuleRepositoryPort {

    private final ModuleRepository jpaModuleRepository;
    private final CourseRepository jpaCourseRepository;

    public ModuleRepositoryAdapter(ModuleRepository jpaModuleRepository,
                                   CourseRepository jpaCourseRepository) {
        this.jpaModuleRepository = jpaModuleRepository;
        this.jpaCourseRepository = jpaCourseRepository;
    }

    @Override
    public Section save(Long courseId, Section section) {
        CourseEntity courseEntity = jpaCourseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        SectionEntity entity = toEntity(section);
        entity.setCourse(courseEntity);

        SectionEntity saved = jpaModuleRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Section> findByCourseId(Long courseId) {
        return jpaModuleRepository.findByCourseIdOrderByOrderIndex(courseId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Section toDomain(SectionEntity entity) {
        Section s = new Section();
        s.setId(entity.getId());
        s.setTitle(entity.getTitle());
        s.setContentUrl(entity.getContentUrl());
        s.setContentType(entity.getContentType());
        s.setOrderIndex(entity.getOrderIndex());
        return s;
    }

    private SectionEntity toEntity(Section domain) {
        SectionEntity e = new SectionEntity();
        e.setId(domain.getId());
        e.setTitle(domain.getTitle());
        e.setContentUrl(domain.getContentUrl());
        e.setContentType(domain.getContentType());
        e.setOrderIndex(domain.getOrderIndex());
        return e;
    }
}
