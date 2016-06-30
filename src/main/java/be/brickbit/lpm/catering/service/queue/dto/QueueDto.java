package be.brickbit.lpm.catering.service.queue.dto;

import java.time.LocalDateTime;

public class QueueDto {
    private Long taskId;
    private String description;
    private Integer duration;
    private String instructions;
    private LocalDateTime startTime;
    private String queueName;
    private String comment;

    public Long getTaskId() {
        return taskId;
    }

    public QueueDto setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public QueueDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public QueueDto setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public QueueDto setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public QueueDto setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getQueueName() {
        return queueName;
    }

    public QueueDto setQueueName(String queueName) {
        this.queueName = queueName;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public QueueDto setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
