package com.emerchantpay.task.models;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Setter
@Getter
public class Transaction {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	private Date creationDateTime;
	
	@Column(unique=true)
	private String uuid;
	
	private double amount;
	
	private String status; //(approved, reversed, refunded, error)
	
	@Column(name="customer_email")
	private String customerEmail;
	
	@Column(name="customer_phone")
	private String customerPhone;
	
	@OneToOne
	private Transaction reference;
	
	@ManyToOne
	private Merchant merchant;
	
	private String type; //(authorize, charge, refund, reversal)
	
}
