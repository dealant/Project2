import java.time.LocalDate;

/**
 * Demo for Stanley's Storage 
 *
 * @author      Bill Barry
 * @version     2020-06-23
 */
public class Main {

    public static void main(String[] args) {
        /* 
         * In most of our projects, main is a place to demonstrate your model
         * and show off your work.  There is no requirement of exactly what
         * must go here, but tell a little story if you can, and make sure
         * you show off the more advanced features.  And if you are doing extra
         * credit, make sure to include that so I don't miss it.
         * 
         * Note also that main is one of the FEW places you can display information;
         * other classes are supplier code and should neither print nor ask the
         * user any questions. 
         */
        
        // Set up location and some sample customers
        StorageLocation loc1 = new StorageLocation("WA23Issaquah");
        loc1.addCustomer(new Customer("Pat Perkins", "425-555-1314"));
        loc1.addCustomer(new Customer("Chris Connoly", "425-555-3141"));
        
        // Display basic information about the location
        System.out.print('\u000c');
        System.out.printf("Storage Location : %s\n",  loc1.getDesignation());
        System.out.printf("Customer count   : %3d\n", loc1.getCustomerCount());
        System.out.printf("Empty unit count : %3d\n", loc1.getEmptyUnits().length);
        
        // Rent some units and display some unit info
        System.out.println("\nRenting two units to Pat Perkins");        
        Customer pat = loc1.getCustomer(0);
        loc1.getStorageUnit(1, 5).rent(pat, LocalDate.now(), 199.95);
        loc1.getStorageUnit(1, 6).rent(pat, LocalDate.now(), 199.95);
        System.out.printf("Empty count               : %3d\n", loc1.getEmptyUnits().length);
        System.out.printf("Pat's unit count          : %3d\n", loc1.getCustomerUnits(loc1.getCustomer(0)).length);
        System.out.printf("Empty standard unit count : %3d\n", loc1.getEmptyUnits(StorageUnit.UnitType.STANDARD).length);
        System.out.printf("Empty humidity unit count : %3d\n", loc1.getEmptyUnits(StorageUnit.UnitType.HUMIDITY).length);
        
        System.out.println("\nShowing storage units, rented and unrented");        
        System.out.println(loc1.getStorageUnit(1, 5));
        System.out.println(loc1.getStorageUnit(0, 4));
        System.out.println();
        
        // Demonstrate charging of rent
        System.out.printf("Pat's balance before charging monthly rent :  $%,7.2f\n", pat.getBalance());        
        double total = loc1.chargeMonthlyRent();
        System.out.printf("Pat's balance after charging monthly rent  :  $%,7.2f\n", pat.getBalance());        
        System.out.printf("Total rent charged for all units           :  $%,7.2f\n", total);
    }

}
