//package com.emu.apps.qcm.rest.controllers.management;
//
//import com.emu.apps.qcm.application.importation.ImportService;
//import com.emu.apps.qcm.domain.model.base.PrincipalId;
//import com.emu.apps.qcm.domain.model.upload.UploadId;
//import com.emu.apps.qcm.rest.controllers.management.tasks.Task;
//import com.emu.apps.qcm.rest.mappers.QcmResourceMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import java.io.IOException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.*;
//import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping(value = MANAGEMENT_API + TASKS, produces = APPLICATION_JSON_VALUE)
//public class TaskController {
//
////    private final SsePushNotificationService ssePushNotificationService;
////    public NotificationsSseController(SsePushNotificationService ssePushNotificationService) {
////        this.ssePushNotificationService = ssePushNotificationService;
////    }
//    private final QcmResourceMapper qcmResourceMapper;
//
//    private final ImportService importService;
//
//    public TaskController(QcmResourceMapper qcmResourceMapper, ImportService importService) {
//        this.qcmResourceMapper = qcmResourceMapper;
//        this.importService = importService;
//    }
//
//    @PostMapping()
//    public void postTask(Task task) {
//
//        //return qcmResourceMapper.uploadToUploadResources(importService.importFile(new UploadId(task.getId()), PrincipalId.of(getPrincipal())));
//
//        return new Task()
//
//    }
//
//
//    @GetMapping("/notifications")
//    public SseEmitter doNotify() {
//        final SseEmitter emitter = new SseEmitter(-1L);
////        ssePushNotificationService.addEmitter(emitter);
////        ssePushNotificationService.pingEmitter();
////        emitter.onCompletion(() -> ssePushNotificationService.removeEmitter(emitter));
////        emitter.onTimeout(() -> ssePushNotificationService.removeEmitter(emitter));
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//
//            executor.execute(() ->
//            {
//                List<DataSet> dataSets = dataSetService.findAll();
//                try {
//                    for (DataSet dataSet : dataSets) {
//
//                        randomDelay();
//                        emitter.send(dataSet);
//                    }
//
//                    emitter.complete();
//
//                } catch (IOException e) {
//                    emitter.completeWithError(e);
//                }
//            });
//            executor.shutdown();
//
//        return emitter;
//        return new ResponseEntity <>(emitter, HttpStatus.OK);
//    }
//}
