package com.emu.apps.qcm.infra.persistence.adapters.mappers;


import com.emu.apps.qcm.domain.mappers.AccountIdMapper;
import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.mappers.custom.IgnoreEntityId;
import com.emu.apps.qcm.infra.persistence.adapters.mappers.custom.IgnoreTechniqualData;
import com.emu.apps.qcm.infra.persistence.adapters.mappers.custom.ModelId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {UuidMapper.class, AccountIdMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountEntityMapper {

    //@Mapping(target = "id", source = "id")
    @ModelId
    Account entityToModel(AccountEntity accountEntity);

    @IgnoreEntityId
    @Mapping(target = "uuid", source = "id")
    AccountEntity modelToEntity(Account account);

    @IgnoreTechniqualData
    AccountEntity modelToEntity(Account account, @MappingTarget AccountEntity accountEntity);

}
