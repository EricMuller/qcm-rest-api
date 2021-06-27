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

package com.emu.apps.qcm.rest.controllers.secured.resources;


import com.emu.apps.qcm.rest.controllers.secured.openui.QuestionView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "QuestionWithTagsOnly")
public class QuestionWithTagsOnlyResource {

    @JsonProperty("uuid")
    @JsonView({QuestionView.Find.class})
    private String uuid;

    @JsonProperty("version")
    @JsonView({QuestionView.Find.class})
    private Long version;

    @JsonProperty("created_by")
    @JsonView({QuestionView.Find.class})
    private String createdBy;

    @JsonProperty("dateCreation")
    @JsonView({QuestionView.Find.class})
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({QuestionView.Find.class})
    private String lastModifiedBy;

    @JsonProperty("dateModification")
    @JsonView({QuestionView.Find.class})
    private ZonedDateTime dateModification;

    @JsonProperty("question")
    @JsonView({QuestionView.Find.class})
    private String questionText;

    @JsonProperty("type")
    @JsonView({QuestionView.Find.class})
    private String type;

    @JsonProperty("status")
    @JsonView({QuestionView.Find.class})
    private String status;

    @JsonProperty("tags")
    @JsonView({QuestionView.Find.class})
    private Set <TagResource> tags;

}
