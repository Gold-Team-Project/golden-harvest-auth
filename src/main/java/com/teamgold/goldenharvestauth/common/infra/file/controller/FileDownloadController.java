package com.teamgold.goldenharvestauth.common.infra.file.controller;

import com.teamgold.goldenharvestauth.common.infra.file.service.FileDownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    @GetMapping("/download")
    public ResponseEntity<?> download(@RequestParam("url") String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty() || fileUrl.equals("-0")) {
            return ResponseEntity.badRequest().build();
        }
        return fileDownloadService.download(fileUrl);
    }
}
