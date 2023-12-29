package com.oga.cqrs.command.commands.organization;

import com.oga.cqrs.command.rest.dto.Organization;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrganizationCommand extends BaseCommand {
    private String id;
    private Organization organization;
}
