package model;


public class CustomerService {
    private DbHandler dbHandler = new DbHandler();

    public boolean registerCustomer(Customer customer) {
        if (dbHandler.customerExists(customer.getDetails().getName(), customer.getDetails().getEmail())) {
            return false;
        } else {
            return dbHandler.registerCustomer(customer);
        }
    }

    public boolean loginCustomer(String username, String password) {
        return dbHandler.customerExists(username, password);
    }
}
