
/**
 * Represents a single customer of the storage facility
 *
 * @author      Bill Barry
 * @version     2017-11-20
 */
public class Customer  {

    /** the customer's name */
    private String name;
    /** the customer's phone number */
    private String phone;
    /** the customer's balance */
    private double balance;
    
    /**
     * Customer Constructor
     *
     * @param   name        the customer's name; must not be null or empty
     * @param   phone       the customer's phone number; must not be null or empty
     */
    public Customer(String name, String phone) {
        setName(name);
        setPhone(phone);
        this.balance = 0.00;
    }
    
    /**
     * Retrieves the customer name
     *
     * @return  the customer's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Retrieves the customer phone number
     *
     * @return  the customer's phone number
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Retrieves the customer balance
     *
     * @return  the customer's current balance
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Assigns a new name to the customer
     *
     * @param   name    the updated customer name; must not be null or empty
     */
    public void setName(String name) {
        if (name == null || name.isEmpty() ) {
            throw new IllegalArgumentException("Name must be non-null and non-empty");
        }
        this.name = name;
    }
    
    /**
     * Assigns a new phone number to the customer
     *
     * @param   phone    the updated customer phone number; must not be null or empty
     */
    public void setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone must be non-null and non-empty");
        }
        this.phone = phone;
    }
    
    /**
     * Charges the customer the specified amount, increasing the customer balance
     *
     * @param   amount      the amount to charge; must not be negative
     * @return              the new balance after the charge has been made
     */
    public double charge(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amounts must be non-negative");
        }
        balance += amount;
        return balance;
    }
    
    /**
     * Credits the customer the specified amount, decreasing the customer balance
     *
     * @param   amount      the amount to charge; must not be negative
     * @return              the new balance after the credit has been made
     */
    public double credit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amounts must be non-negative");
        }
        balance -= amount;
        return balance;
    }

}
