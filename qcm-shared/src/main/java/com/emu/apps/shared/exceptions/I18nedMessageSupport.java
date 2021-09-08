package com.emu.apps.shared.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class I18nedMessageSupport {

    public static final String UNKNOWN_UUID_QUESTIONNAIRE = "questionnaire.uuid.unknown";

    public static final String UNKNOWN_UUID_QUESTION = "question.uuid.unknown";

    public static final String UNKNOWN_UUID_UPLOAD = "upload.uuid.unknown";

    public static final String UNKNOWN_UUID_USER = "user.uuid.unknown";

    public static final String INVALID_EMAIL_USER = "user.email.invalid";

    public static final String INVALID_UUID_QUESTION = "question.uuid.invalid";

    public static final String INVALID_UUID_QUESTIONNAIRE = "questionnaire.uuid.invalid";

    public static final String EXISTING_UUID_ACCOUNT = "user.uuid.exists";

    public static final String UNKNOWN_UUID_WEBHOOK = "webhook.uuid.unknown";


}
