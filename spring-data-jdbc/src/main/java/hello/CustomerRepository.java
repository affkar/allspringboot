package hello;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository{

    public void createCustomer(String firstName, String lastName);

    public String getCustomerIdByFirstNameAndLastname(String firstName, String lastName);

    public List<Customer> getAllCustomers();

}
