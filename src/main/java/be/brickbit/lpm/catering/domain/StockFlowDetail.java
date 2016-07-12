package be.brickbit.lpm.catering.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "STOCK_FLOW_DETAIL")
public @Data class StockFlowDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "STOCK_PRODUCT_ID")
	private StockProduct stockProduct;

	@Column(name = "QUANTITY")
	private Integer quantity;
}
