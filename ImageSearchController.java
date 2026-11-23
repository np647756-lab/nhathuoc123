package com.hkcare.controller;

import com.hkcare.model.ApiResponse;
import com.hkcare.service.ImageSearchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/image-search")
public class ImageSearchController {
    private final ImageSearchService service;

    public ImageSearchController(ImageSearchService service) { this.service = service; }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, Object>> search(@RequestParam("image") MultipartFile image) throws IOException {
        List<Map<String, Object>> results = service.timKiem(image);
        return ApiResponse.ok(Map.of("results", results));
    }
}
