package be.brickbit.lpm.catering.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PREPARATION")
public @Data class ProductPreparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "QUEUE_NAME")
    private String queueName;

    @Column(name = "TIMER")
    private Integer timer;

    @Column(name = "INSTRUCTIONS")
    private String instructions;
}
