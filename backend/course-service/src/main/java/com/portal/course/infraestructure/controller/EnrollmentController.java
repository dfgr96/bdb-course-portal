package com.portal.course.infraestructure.controller;

import com.portal.course.application.IEnrollmentService;
import com.portal.dto.EnrollmentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final IEnrollmentService service;

    public EnrollmentController(IEnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDto> enroll(@RequestParam Long userId, @RequestParam Long courseId) {
        return ResponseEntity.ok(service.enroll(userId, courseId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<EnrollmentDto>> getUserEnrollments(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserEnrollments(userId));
    }
}
