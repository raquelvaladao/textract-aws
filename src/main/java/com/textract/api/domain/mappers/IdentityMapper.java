package com.textract.api.domain.mappers;

import com.textract.api.domain.models.Identity;
import com.textract.api.view.responses.CleanedIDResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring")
public interface IdentityMapper {

    Identity dtoToEntity(CleanedIDResponse dto);

    CleanedIDResponse entityToDto(Identity entity);

}
