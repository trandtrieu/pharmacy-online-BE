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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
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
	private String p_component;

	@Column
	private String p_vitue;

	@Column
	private String p_object;

	@Column
	private String p_guide;

	@Column
	private String p_preservation;

	@Column
	private String p_instruction;

	@Column
	private String p_store;

	@Column
	private String p_madeIn;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;




}
