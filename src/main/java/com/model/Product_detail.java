package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_detail")
public class Product_detail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int detail_id;

	@Column
	private String p_Ingredients ;

	@Column
	private String p_Indications;

	@Column
	private String p_Contraindications ;

	@Column
	private String p_DosageAndUsage;

	@Column
	private String p_SideEffects;

	@Column
	private String p_Precautions;

	@Column
	private String p_DrugInteractions;
	
	@Column
	private String p_Storage;
	
	@Column
	private String p_Packaging;

	@Column
	private String p_madeIn;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;




}
