package com.portal.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ViewCourses {
    private List<Course> availableCourses = new ArrayList<>();
    private List<Course> activeCourses = new ArrayList<>();

    public ViewCourses(List<Course> availableCourses, List<Course> activeCourses) {
        this.availableCourses = validateList(availableCourses);
        this.activeCourses = validateList(activeCourses);
    }

    private List<Course> validateList(List<Course> courses) {
        return (courses == null || courses.isEmpty())
                ? new ArrayList<>()
                : new ArrayList<>(courses);
    }
}
