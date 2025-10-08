package com.portal.course.application;

import com.portal.dto.Course;
import com.portal.dto.Section;
import com.portal.dto.ViewCourses;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICourseService {

    ViewCourses getAllCourses(Long userId);
    Course getCourse(Long id);
    Course createCourse(Course course, List<MultipartFile> files);
    Section addModule(Long courseId, Section module);
}
