package be.brickbit.lpm.catering.service.queue.dto;

import java.time.LocalDateTime;

public class KitchenQueueDto {
    private Long taskId;
    private String description;
    private Integer duration;
    private String instructions;
    private LocalDateTime startTime;
    private String queueName;

    public Long getTaskId() {
        return taskId;
    }

    public KitchenQueueDto setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public KitchenQueueDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public KitchenQueueDto setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public KitchenQueueDto setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public KitchenQueueDto setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getQueueName() {
        return queueName;
    }

    public KitchenQueueDto setQueueName(String queueName) {
        this.queueName = queueName;
        return this;
    }
}
