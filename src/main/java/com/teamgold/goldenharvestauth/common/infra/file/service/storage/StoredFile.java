package com.teamgold.goldenharvestauth.common.infra.file.service.storage;

import com.teamgold.goldenharvestauth.common.infra.file.domain.FileContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoredFile {
    private String fileUrl;
    private String originalName;
    private String uuidFilename;
    private FileContentType contentType;
}
