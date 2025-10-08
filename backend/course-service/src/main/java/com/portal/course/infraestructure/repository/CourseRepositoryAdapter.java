package com.portal.course.infraestructure.repository;

import com.portal.dto.Course;
import com.portal.dto.Section;
import com.portal.course.domain.repository.CourseRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseRepositoryAdapter implements CourseRepositoryPort {

    private final CourseRepository jpaCourseRepository;

    public CourseRepositoryAdapter(CourseRepository jpaCourseRepository) {
        this.jpaCourseRepository = jpaCourseRepository;
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = toEntity(course);
        return toDomain(jpaCourseRepository.save(entity));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jpaCourseRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Course> findActiveAll(Long userId) {
        return jpaCourseRepository.findActiveCoursesByUserId(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Course> findAvailableAll(Long userId) {
        return jpaCourseRepository.findCoursesNotEnrolledByUserId(userId)
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

    private Course toDomain(CourseEntity entity) {
        Course c = new Course();
        c.setId(entity.getId());
        c.setTitle(entity.getTitle());
        c.setDescription(entity.getDescription());
        c.setCategory(entity.getCategory());
        if (entity.getModules() != null) {
            c.setModules(
                    entity.getModules().stream()
                            .map(this::toDomain)
                            .toList()
            );
        }
        return c;
    }

    private CourseEntity toEntity(Course domain) {
        CourseEntity e = new CourseEntity();
        e.setId(domain.getId());
        e.setTitle(domain.getTitle());
        e.setDescription(domain.getDescription());
        e.setCategory(domain.getCategory());
        if (domain.getModules() != null) {
            List<SectionEntity> sections = domain.getModules().stream()
                    .map(this::toEntity)
                    .toList();
            sections.forEach(s -> s.setCourse(e));
            e.setModules(sections);
        }
        return e;
    }

    private SectionEntity toEntity(Section domain) {
        SectionEntity e = new SectionEntity();
        e.setId(domain.getId());
        e.setTitle(domain.getTitle());
        e.setContentUrl(domain.getContentUrl());
        e.setContentType(domain.getContentType());
        return e;
    }
}
