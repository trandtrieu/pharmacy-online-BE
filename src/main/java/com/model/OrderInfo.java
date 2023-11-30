package com.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "OrderInfo")
@ToString
public class OrderInfo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String accountId;
	private String amount;
    private String paymentMethod;
    private String deliveryMethod;
    private String name;
    private String phone;
    @Column(columnDefinition = "nvarchar(max)")
    private String address;
    @Column(columnDefinition = "nvarchar(max)")
    private String note;
    private String date;
    private String status;
    @OneToMany(mappedBy = "orderInfo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductInfo> products;
    
}