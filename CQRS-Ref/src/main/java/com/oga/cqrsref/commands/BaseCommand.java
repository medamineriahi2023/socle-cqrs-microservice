package com.oga.cqrsref.commands;
import com.oga.cqrsref.messages.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseCommand extends Message {
/*    public BaseCommand(String ids){
        super(ids);
    }*/

}