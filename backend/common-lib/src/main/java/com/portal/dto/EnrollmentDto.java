package com.portal.dto;

import java.sql.Timestamp;

public record EnrollmentDto(Long saveId, Long courseId, Long userId, String status, Timestamp enrolledAt) {
}
