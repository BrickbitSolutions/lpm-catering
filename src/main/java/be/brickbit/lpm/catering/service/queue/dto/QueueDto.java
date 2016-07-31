package be.brickbit.lpm.catering.service.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QueueDto {
    private Long taskId;
    private String description;
    private LocalDateTime startTime;
    private Integer duration;
    private Long timeRemaining;
    private String instructions;
    private String queueName;
    private String comment;
}
