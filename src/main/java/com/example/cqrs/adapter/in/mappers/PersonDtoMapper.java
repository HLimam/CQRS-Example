package com.example.cqrs.adapter.in.mappers;

import com.example.cqrs.domain.models.Person;
import com.example.cqrs.kernel.mappers.GenericDTOMapper;
import com.example.cqrs.adapter.in.payload.dto.PersonDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PersonDtoMapper extends GenericDTOMapper<Person, PersonDTO> {

}
