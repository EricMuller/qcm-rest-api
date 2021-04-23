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

package com.emu.apps.qcm.infra.persistence.utils;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;
import com.emu.apps.shared.exceptions.EntityNotFoundException;

import java.util.Objects;
import java.util.Optional;

public final class RaiseExceptionUtil {

    private RaiseExceptionUtil() {
        //nope
    }

    public static <T extends Object> void raiseEntityNotFoundException(String uuid, String message) {
        throw new EntityNotFoundException(uuid, message);
    }

    public static <T extends Object> void raiseIfNull(String uuid, T entity, String message) {
        if (Objects.isNull(entity)) {
            throw new EntityNotFoundException(uuid, message);
        }
    }

    public static <T extends AuditableEntity> void raiseIfNull(T entity, String message) {
        if (Objects.isNull(entity)) {
            throw new EntityNotFoundException(message);
        }
    }

    public static void raiseIfNull(Optional <? extends AuditableEntity> entity, String message) {
        if (!entity.isPresent()) {
            throw new EntityNotFoundException(message);
        }
    }

    public static void raiseNoteFoundException(String message) {
        throw new EntityNotFoundException(message);
    }


//    public static <U> U ifPresentOrElse(Optional <T> optional, Function<? extends T,? extends U> action, Runnable emptyAction) {
//
//        if (optional.isPresent()) {
//            return action.accept(optional.get());
//        } else {
//            emptyAction.run();
//        }
//        return null;
//    }

//    @FunctionalInterface
//    public interface Consumer<T,U> {
//        D accept(T t);
//    }
}
