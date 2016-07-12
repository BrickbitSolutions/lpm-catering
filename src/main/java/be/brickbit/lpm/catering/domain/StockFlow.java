package be.brickbit.lpm.catering.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "STOCK_FLOW")
public @Data class StockFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private StockFlowType stockFlowType;

    @Column(name = "TIME_ON_ENTRY")
    private LocalDateTime timestamp;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "STOCK_FLOW_ID", nullable = false)
    private List<StockFlowDetail> details;
}