package com.example.cqrs.adapter.out;

import com.example.cqrs.adapter.out.entities.PersonJPA;
import com.example.cqrs.adapter.out.projections.PersonProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "autocomplete", path="persons", excerptProjection = PersonProjection.class)
public interface PersonJPARepository extends JpaRepository<PersonJPA, Integer> {
    @RestResource(path = "by-first-name")
    List<PersonJPA> findByFirstNameContainingIgnoreCase(@Param("firstName") String firstName);
}
