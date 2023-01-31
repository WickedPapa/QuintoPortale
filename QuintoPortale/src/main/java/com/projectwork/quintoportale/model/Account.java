package com.projectwork.quintoportale.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	private String encryptedPassword;
	private String name;
	private String surname;
	private String email;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Account(String username, String encryptedPassword, String name, String surname, String email) {
		super();
		this.username = username;
		this.encryptedPassword = encryptedPassword;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}



	public Account(int id, String username, String encryptedPassword, String name, String surname, String email) {
		super();
		this.id = id;
		this.username = username;
		this.encryptedPassword = encryptedPassword;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
