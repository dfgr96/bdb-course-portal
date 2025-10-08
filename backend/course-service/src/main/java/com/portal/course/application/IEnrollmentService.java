package com.portal.course.application;

import com.portal.dto.EnrollmentDto;

import java.util.List;

public interface IEnrollmentService {

    EnrollmentDto enroll(Long userId, Long courseId);
    List<EnrollmentDto> getUserEnrollments(Long userId);
}
