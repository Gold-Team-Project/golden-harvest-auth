package com.teamgold.goldenharvestgateway.common.infra.file.domain.dto;


import com.teamgold.goldenharvestgateway.common.infra.file.domain.FileContentType;

public record FileUploadResponse(
        Long fileId,
        String fileUrl,
        FileContentType contentType
) {}
