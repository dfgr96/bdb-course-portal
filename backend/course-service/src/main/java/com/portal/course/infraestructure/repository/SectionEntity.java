package com.portal.course.infraestructure.repository;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.portal.enums.ContentType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "modules")
@Data
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private CourseEntity course;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(name = "content_url", length = 500)
    private String contentUrl;

    @Column(name = "order_index")
    private Integer orderIndex;
}
