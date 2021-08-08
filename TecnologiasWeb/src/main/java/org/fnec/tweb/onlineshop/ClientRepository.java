package org.fnec.tweb.onlineshop;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

	List<Client> findByFirstName(String firstName);

	Client findOneByUsername(String username);

	Client findById(long id);
}
