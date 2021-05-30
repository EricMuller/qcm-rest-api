package com.emu.apps.qcm.infra.persistence.adapters.mappers;


import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import com.emu.apps.qcm.domain.mappers.AccountIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {UuidMapper.class, AccountIdMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountEntityMapper {

    @Mapping(target = "id", source = "uuid")
    Account modelToDto(AccountEntity accountEntity);


    @Mapping(target = "id", ignore = true)
    AccountEntity dtoToModel(Account account);

    @Mapping(target = "id", ignore = true)
    AccountEntity dtoToModel(@MappingTarget AccountEntity accountEntity, Account account);

}
