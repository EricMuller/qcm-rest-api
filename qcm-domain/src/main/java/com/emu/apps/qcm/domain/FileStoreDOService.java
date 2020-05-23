package com.emu.apps.qcm.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileStoreDOService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FileStoreDOService.class);

    @Value("${store.directory.path:'/tmp/'}")
    private String path;

    public File store(InputStream inputStream, String fileName) throws IOException {


        File file = new File(getPath().concat(fileName));

        try (OutputStream out = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        return file;
    }

    public String getPath() {
        return path;
    }
}
