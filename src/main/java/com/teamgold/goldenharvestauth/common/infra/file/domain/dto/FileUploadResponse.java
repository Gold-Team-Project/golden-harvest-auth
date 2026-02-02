package com.teamgold.goldenharvestauth.common.infra.file.domain.dto;


import com.teamgold.goldenharvestauth.common.infra.file.domain.FileContentType;

public record FileUploadResponse(
        Long fileId,
        String fileUrl,
        FileContentType contentType
) {}
