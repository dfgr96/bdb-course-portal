package com.portal.course.infraestructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.course.application.CourseService;
import com.portal.dto.Course;
import com.portal.dto.Section;
import com.portal.dto.ViewCourses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/user/{userId}")
    public ViewCourses getAllCourses(@PathVariable Long userId) {
        return courseService.getAllCourses(userId);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Course> createCourse(@RequestPart("course") String courseJson,
                                               @RequestPart("files") List<MultipartFile> files) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Course course = objectMapper.readValue(courseJson, Course.class);

            Course saved = courseService.createCourse(course, files);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/modules")
    public ResponseEntity<Section> addModule(@PathVariable Long id, @RequestBody Section module) {
        return ResponseEntity.ok(courseService.addModule(id, module));
    }
}
