package be.brickbit.lpm.catering.domain;

import javax.persistence.*;

@Entity
@Table(name = "PREPARATION")
public class ProductPreparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "QUEUE_NAME")
    private String queueName;

    @Column(name = "TIMER")
    private Integer timer;

    @Column(name = "INSTRUCTIONS")
    private String instructions;

    public Long getId() {
        return id;
    }

    public void setId(Long someId) {
        id = someId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String someQueueName) {
        queueName = someQueueName;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer someTimer) {
        timer = someTimer;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String someInstructions) {
        instructions = someInstructions;
    }
}
