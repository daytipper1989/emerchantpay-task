package com.emerchantpay.task.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.emerchantpay.task.configurations.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "merchants")
@Setter
@Getter
public class Merchant implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5000627922962061083L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@Column(unique = true)
	private String email;

	private String status; // active or inactive

	@Column(name = "total_transaction_sum")
	private double totalTransactionSum;

	private boolean admin;

	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return admin ? List.of(new SimpleGrantedAuthority(Role.ADMIN.name()))
				: List.of(new SimpleGrantedAuthority(Role.MERCHANT.name()));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return "active".equals(status) ? true : false;
	}

}
