package com.example.cqrs.adapter.in.payload.queries;

import com.example.cqrs.kernel.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPersonByIdQuery implements Query {
   private Integer personId;
}