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

package com.emu.apps.qcm.webflux.handlers.clients;

import com.emu.apps.qcm.web.dtos.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Profile("webflux")
//@RestController
//@CrossOrigin
//@RequestMapping(QcmGatewayApi.API_V1 + "/categories")

public class WebClientCategoryHandler extends WebClientGatewayHandler {

    @Autowired
    public WebClientCategoryHandler(WebClient webClient) {
        super(webClient);
    }


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux <CategoryDto> getCategories(ServerHttpRequest request, Authentication authentication) {
        return webClient
                .get()
                .uri(getServiceUri(request))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(getTokenValue(authentication)))
                .retrieve()
                .bodyToFlux(CategoryDto.class);
    }


    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono <CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto, ServerHttpRequest request, Authentication authentication) {
        return webClient
                .post()
                .uri(getServiceUri(request))
                .body(BodyInserters.fromValue(categoryDto))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(getTokenValue(authentication)))
                .retrieve()
                .bodyToMono(CategoryDto.class);
    }

    @GetMapping(value = "{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono <CategoryDto> getCategory(@PathVariable("id") Long id, ServerHttpRequest request, Authentication authentication) {
        return webClient
                .get()
                .uri(getServiceUri(request))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(getTokenValue(authentication)))
                .retrieve()
                .bodyToMono(CategoryDto.class);
    }
}
