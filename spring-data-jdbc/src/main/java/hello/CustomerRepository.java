package hello;

import java.util.List;

public interface CustomerRepository{

    void createCustomer(String firstName, String lastName);

    String getCustomerIdByFirstNameAndLastname(String firstName, String lastName);

    List<Customer> getAllCustomers();

    void deleteCustomersByFirstName(String firstName);

    void deleteCustomersByLastName(String lastName);

    void deleteCustomerById(Long id);

    void deleteAllCustomers();

}
