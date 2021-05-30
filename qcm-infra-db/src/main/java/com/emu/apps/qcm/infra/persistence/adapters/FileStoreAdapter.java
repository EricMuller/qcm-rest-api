package com.emu.apps.qcm.infra.persistence.adapters;

import com.emu.apps.qcm.infra.persistence.FileStorePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileStoreAdapter implements FileStorePort {

    @Value("${store.directory.path:'/tmp/'}")
    private String path;

    @Override
    public File store(InputStream inputStream, String fileName) throws IOException {


        File file = new File(getPath().concat(fileName));

        try (OutputStream out = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        }
        return file;
    }

    @Override
    public String getPath() {
        return path;
    }
}
