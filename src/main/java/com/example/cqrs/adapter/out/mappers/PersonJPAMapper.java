package com.example.cqrs.adapter.out.mappers;


import com.example.cqrs.domain.models.Person;
import com.example.cqrs.adapter.out.entities.PersonJPA;
import com.example.cqrs.kernel.mappers.GenericJPAMapper;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PersonJPAMapper extends GenericJPAMapper<Person, PersonJPA> {

}
