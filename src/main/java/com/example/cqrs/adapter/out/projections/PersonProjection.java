package com.example.cqrs.adapter.out.projections;

import java.time.LocalDate;

public interface PersonProjection {
    Integer getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    LocalDate getBirthDate();
    String getPhoto();
}
