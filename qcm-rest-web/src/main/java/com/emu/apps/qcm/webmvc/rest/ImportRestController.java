package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.ports.ImportServicePort;
import com.emu.apps.qcm.models.UploadDto;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.IMPORTS;
import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PUBLIC_API;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + IMPORTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class ImportRestController {

    private final ImportServicePort importServicePort;

//    private ExecutorService executor = Executors.newCachedThreadPool();

    public ImportRestController(ImportServicePort importServicePort) {
        this.importServicePort = importServicePort;
    }

    @ResponseBody
    @GetMapping(value = "/{uuid}")
    public UploadDto importFileByUploadUuid(@PathVariable("uuid") String uploadUuid) throws IOException {
        return importServicePort.importFile(uploadUuid, AuthentificationContextHolder.getUser());
    }

//    @ResponseBody
//    @GetMapping(value = "/{uuid}/import")
//    public ResponseEntity<ResponseBodyEmitter> importFile(@PathVariable("uuid") String uploadUuid) throws IOException {
////
////        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
////        executor.execute(() -> {
////            try {
////                emitter.send("/rbe" + " @ " + new Date(), MediaType.TEXT_PLAIN);
////                emitter.complete();
////            } catch (Exception ex) {
////                emitter.completeWithError(ex);
////            }
////        });
////
////
////        return new ResponseEntity(emitter, HttpStatus.OK);
//
//    }



}
