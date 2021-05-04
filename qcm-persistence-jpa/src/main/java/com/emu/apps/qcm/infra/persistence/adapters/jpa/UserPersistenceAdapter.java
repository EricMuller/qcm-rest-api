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

package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.model.user.User;
import com.emu.apps.qcm.infra.persistence.UserPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.UserEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.UserRepository;
import com.emu.apps.qcm.infra.persistence.mappers.UserEntityMapper;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.emu.apps.shared.exceptions.MessageSupport.UNKNOWN_UUID_USER;
import static java.util.Objects.nonNull;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    private final UserEntityMapper userMapper;

    @Autowired
    public UserPersistenceAdapter(UserRepository tagRepository, UserEntityMapper userMapper) {
        this.userRepository = tagRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity;
        if (Objects.isNull(user.getUuid())) {
            userEntity = userMapper.dtoToModel(user);
        } else {
            userEntity = userRepository.findByUuid(UUID.fromString(user.getUuid()))
                    .orElseThrow(() -> new EntityNotFoundException(user.getUuid(), UNKNOWN_UUID_USER));
            userMapper.dtoToModel(userEntity, user);
        }
        userEntity = nonNull(userEntity) ? userRepository.save(userEntity) : null;

        return userMapper.modelToDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmailEquals(String email) {
        return userMapper.modelToDto(userRepository.findByEmailEquals(email).orElse(null));
    }

}
