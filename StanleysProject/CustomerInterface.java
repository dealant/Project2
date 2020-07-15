/**
 * Requirements for the Customer class
 *
 * @author      Bill Barry
 * @version     2020-04-30
 */
public interface CustomerInterface {
    // note:  constructor should accept name and phone parameters, in that order
    public String getName();
    public String getPhone();
    public double getBalance();
    public void setName(String name);
    public void setPhone(String phone);
    public double charge(double amount);
    public double credit(double amount);
}
