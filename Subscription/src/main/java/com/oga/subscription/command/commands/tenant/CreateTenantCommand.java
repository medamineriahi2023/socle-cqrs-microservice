package com.oga.subscription.command.commands.tenant;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/**
 * Represents a command to create a tenant.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTenantCommand extends BaseCommand {
    private UUID tenantId ;
    private String externalKey ;
    private String apiKey ;
    private String apiSecret ;
}
