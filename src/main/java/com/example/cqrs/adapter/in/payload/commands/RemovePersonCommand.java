package com.example.cqrs.adapter.in.payload.commands;

import com.example.cqrs.kernel.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemovePersonCommand implements Command {
    private Integer id;
}
