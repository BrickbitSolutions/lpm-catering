package be.brickbit.lpm.core.client.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotifyCommand {
    private Long userId;
    private String message;
}
