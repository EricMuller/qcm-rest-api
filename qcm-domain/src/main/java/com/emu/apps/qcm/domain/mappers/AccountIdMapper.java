package com.emu.apps.qcm.domain.mappers;

import com.emu.apps.qcm.domain.model.user.AccountId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountIdMapper {

    default AccountId toAccountId(UUID uuid) {
        return new AccountId(uuid.toString());
    }

    default AccountId toAccountId(String uuid) {
        return new AccountId(uuid);
    }

    default String accountIdIdToUuid(AccountId id) {
        return Objects.nonNull(id) ? id.toUuid() : null;
    }

}
