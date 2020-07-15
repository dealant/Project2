import java.time.LocalDate;

/**
 * Requirements for the StorageUnit class
 *
 * @author      Bill Barry
 * @version     2020-04-30
 */
public interface StorageUnitInterface {
    // note: constructor should take width, length, height, and StorageLocation parameters, in that order
    public int getWidth();
    public int getLength();
    public int getHeight();
    public double getPrice();
    public Customer getCustomer();
    public boolean isRented();
    public LocalDate getRentalStart();
    public StorageLocation getStorageLocation();
    public boolean rent(Customer customer, LocalDate rentalStart);
    public boolean release();
    public String toString();
    public double calcUnitSpecificPrice();
    
    //note: one subclass constructor should take parameters for:
    //      width, length, height, StorageLocation
    //the others will take
    //      width, length, height, ______ (one other integer), StorageLocation

}
