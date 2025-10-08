package com.portal.course.application;

import com.portal.dto.CourseProgressDto;
import com.portal.dto.ModuleProgressDto;
import com.portal.dto.ProgressDto;

import java.util.List;

public interface IProgressService {
    ProgressDto updateProgress(Long userId, Long moduleId, Integer percent);
    List<ModuleProgressDto> getProgressByCourse(Long userId, Long courseId);
    List<CourseProgressDto> getUserCoursesProgress(Long userId);
}
