package com.EmailVerification.EmailAuthentification;


public class Customer {
    
    private String name;
    private String email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", email=" + email + ", getName()=" + getName() + ", getEmail()=" + getEmail()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public Customer(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Constructors, getters, and setters
}
