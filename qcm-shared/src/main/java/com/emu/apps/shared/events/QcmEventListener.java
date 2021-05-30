package com.emu.apps.shared.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QcmEventListener {

    @EventListener
    public void handleEvent(AppEvent appEvent) {
        LOGGER.info("Reception Event {} ", appEvent.getMessage());
        //ssePushNotificationService.doNotify(alimEvent);
    }

}
