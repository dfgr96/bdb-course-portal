package com.portal.course.application;

import com.portal.dto.Course;
import com.portal.dto.Section;
import com.portal.course.domain.repository.CourseRepositoryPort;
import com.portal.course.domain.repository.ModuleRepositoryPort;
import com.portal.dto.ViewCourses;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CourseService implements ICourseService{

    private final CourseRepositoryPort courseRepository;
    private final ModuleRepositoryPort moduleRepository;
    private final IS3Service s3Service;

    public CourseService(CourseRepositoryPort courseRepository, ModuleRepositoryPort moduleRepository, IS3Service s3Service) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.s3Service = s3Service;
    }

    @Override
    public ViewCourses getAllCourses(Long userId) {
        return new ViewCourses(courseRepository.findAvailableAll(userId), courseRepository.findActiveAll(userId));
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course createCourse(Course course, List<MultipartFile> files) {

        List<String> urls = s3Service.uploadFiles(files, "courses/" + course.getTitle().replace(" ", "_"));

        for (int i = 0; i < course.getModules().size(); i++) {
            Section module = course.getModules().get(i);
            module.setCourse(course);
            module.setContentUrl(urls.get(i));
        }
        return courseRepository.save(course);
    }

    @Override
    public Section addModule(Long courseId, Section module) {
        return moduleRepository.save(courseId, module);
    }
}
