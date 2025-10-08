package com.portal.course.application;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IS3Service {

    List<String> uploadFiles(List<MultipartFile> files, String folder);
}
