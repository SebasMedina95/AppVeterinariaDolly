package com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.uploads;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ValidImageType {

    public boolean isValidImageType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
               contentType.equals("image/jpeg") ||
               contentType.equals("image/jpg") ||
               contentType.equals("image/png")
        );
    }

}
