package com.portal.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Course {

    private Long id;
    private String title;
    private String description;
    private List<Section> modules = new ArrayList<>();

    public Course() {}

    public Course(Long id, String title, String description, List<Section> modules) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.modules = modules != null ? modules : new ArrayList<>();
    }
}
