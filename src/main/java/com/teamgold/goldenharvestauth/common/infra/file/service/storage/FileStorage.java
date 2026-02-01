package com.teamgold.goldenharvestauth.common.infra.file.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorage {
    StoredFile store(MultipartFile file) throws IOException;
}
