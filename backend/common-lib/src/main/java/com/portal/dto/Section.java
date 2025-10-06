package com.portal.dto;

import com.portal.enums.ContentType;
import lombok.Data;

@Data
public class Section {

    private Long id;
    private Course course;
    private String title;
    private ContentType contentType;  // VIDEO, PDF, IMAGE, OTHER
    private String contentUrl;
    private Integer orderIndex;

    public Section() {}

    public Section(Long id, String title, ContentType contentType, String contentUrl) {
        this.id = id;
        this.title = title;
        this.contentType = contentType;
        this.contentUrl = contentUrl;
    }
}
