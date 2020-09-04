package com.emu.apps.qcm.spi.persistence.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageSupport {

    public static final String UNKNOWN_UUID_QUESTIONNAIRE = "questionnaire.uuid.unknown";

    public static final String UNKNOWN_UUID_QUESTION = "question.uuid.unknown";

    public static final String UNKNOWN_UUID_UPLOAD = "upload.uuid.unknown";

    public static final String UNKNOWN_UUID_USER = "user.uuid.unknown";

    public static final String UNKNOWN_UUID_WEBHOOK = "webhook.uuid.unknown";

}
