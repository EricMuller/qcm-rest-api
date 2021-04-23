package com.emu.apps.qcm.infra.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FileStorePort {
    File store(InputStream inputStream, String fileName) throws IOException;

    String getPath();
}
