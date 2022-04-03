/*
 *
 * The MIT License (MIT)
 *
 * Copyright (c)  2019 qcm-rest-api
 * Author  Eric Muller
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

package com.emu.apps.qcm.rest.controllers;

public final class ApiRestMappings {

    public static final String PROTECTED_API = "/api/v1/qcm/protected/";

    public static final String PUBLICIZED_API = "/api/v1/qcm/public/";

    public static final String LOGS = "/api/logs";

    public static final String QUESTIONNAIRES = "/questionnaires";

    public static final String CATEGORIES = "/categories";

    public static final String QUESTIONS = "/questions";

    public static final String SUGGEST = "/suggest";

    public static final String TAGS = "/tags";

    public static final String STATUS = "/status";

    public static final String UPLOADS = "/uploads";

    public static final String WEBHOOKS = "/webhooks";

    public static final String ACCOUNTS = "/accounts";

    public static final String EXPORTS = "/exports";

    public static final String IMPORTS = "/imports";

    public static final String QUERY = "/query";

    public static final String COMMAND = "/command";



    private ApiRestMappings() {
        //nop
    }

}
