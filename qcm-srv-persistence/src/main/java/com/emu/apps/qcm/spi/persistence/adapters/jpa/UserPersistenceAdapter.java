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

package com.emu.apps.qcm.spi.persistence.adapters.jpa;

import com.emu.apps.qcm.aggregates.User;
import com.emu.apps.qcm.spi.persistence.UserPersistencePort;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.UserEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.UserRepository;
import com.emu.apps.qcm.spi.persistence.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.spi.persistence.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport.UNKNOWN_UUID_USER;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserPersistenceAdapter(UserRepository tagRepository, UserMapper userMapper) {
        this.userRepository = tagRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User userDto) {
        UserEntity user;
        if (Objects.isNull(userDto.getUuid())) {
            user = userMapper.dtoToModel(userDto);
        } else {
            user = userRepository.findByUuid(UUID.fromString(userDto.getUuid())).orElse(null);
            RaiseExceptionUtil.raiseIfNull(userDto.getUuid(), user, UNKNOWN_UUID_USER);
            userMapper.dtoToModel(user, userDto);
        }
        user = Objects.nonNull(user) ? userRepository.save(user) : null;

        return userMapper.modelToDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmailEquals(String email) {
        return userMapper.modelToDto(userRepository.findByEmailEquals(email).orElse(null));
    }

}
