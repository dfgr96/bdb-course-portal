package com.portal.dto;

import java.sql.Timestamp;

public record ProgressDto(Long saveId, Long userId, Long moduleId, int percent, Timestamp completedAt) {
}
