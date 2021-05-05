package com.emu.apps.qcm.domain.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QcmEventListener {

    @EventListener
    public void handleEvent(QcmEvent qcmEvent) {
        LOGGER.info("Reception Event {} ", qcmEvent.getMessage());
        //ssePushNotificationService.doNotify(alimEvent);
    }

}
