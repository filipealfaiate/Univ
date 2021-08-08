package org.fnec.tweb.onlineshop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String mail, firstName, username, password, address, role;

	@OneToMany(mappedBy = "client")
    	private List<Orders> orders;

	protected Client() {}

	public Client(String mail, String firstName, String username, String password, String address, String role) {
		this.mail = mail;
		this.firstName = firstName;
		this.username = username;
		this.password = password;
		this.address = address;
		this.role = role;
	}

	@Override
	public String toString() {
		return String.format(
				"Client[id=%d, mail='%s', firstName='%s', username='%s', password='%s', address='%s', role='%s']",
				id, mail, firstName, username, password, address, role);
	}

	public Long getId() {
		return id;
	}

	public String getMail() {
		return mail;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public String getRole() {
		return role;
	}
}
