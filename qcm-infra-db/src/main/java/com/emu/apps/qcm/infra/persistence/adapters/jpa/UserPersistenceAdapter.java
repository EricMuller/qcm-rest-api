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

import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.infra.persistence.UserPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.AccountRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.AccountEntityMapper;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_USER;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional
public class UserPersistenceAdapter implements UserPersistencePort {

    private final AccountRepository accountRepository;

    private final AccountEntityMapper accountEntityMapper;

    @Autowired
    public UserPersistenceAdapter(AccountRepository tagRepository, AccountEntityMapper accountEntityMapper) {
        this.accountRepository = tagRepository;
        this.accountEntityMapper = accountEntityMapper;
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity;

        accountEntity = accountEntityMapper.modelToEntity(account);

        accountEntity = nonNull(accountEntity) ? accountRepository.save(accountEntity) : null;

        return accountEntityMapper.entityToModel(accountEntity);
    }

    @Override
    public Account update(Account account) {
        AccountEntity accountEntity;
        if (isNull(account.getId()) || isNull(account.getId().toUuid())) {
            throw new I18nedNotFoundException(UNKNOWN_UUID_USER, "");
        } else {
            accountEntity = accountRepository.findByUuid(UUID.fromString(account.getId().toUuid()))
                    .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_USER, account.getId().toUuid()));
            accountEntityMapper.modelToEntity(account, accountEntity);
        }
        accountEntity = nonNull(accountEntity) ? accountRepository.save(accountEntity) : null;

        return accountEntityMapper.entityToModel(accountEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Account findByEmailEquals(String email) {
        return accountEntityMapper.entityToModel(accountRepository.findByEmailEquals(email).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public Account findById(String id) {
        return accountEntityMapper.entityToModel(accountRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_USER, id))
        );
    }

}
