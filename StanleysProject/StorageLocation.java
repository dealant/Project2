/**
 * Represents a storage location for Stanley's Storage Spots
 *
 * @author      Bill Barry
 * @version     2017-11-20
 */
public class StorageLocation  {

    //---------------------------------------------------------------------
    //          CONSTANTS
    //---------------------------------------------------------------------
    /** the number of rows of units in this location */
    public static final int NUM_ROWS      =  12;
    /** the number of rental units in each row */
    public static final int NUM_SPACES    =  20;
    /** the initial number of customers */
    public static final int NUM_CUSTOMERS = 100;
    /** the row at which standard units begin */
    public static final int ROW_START_STD = 0;
    /** the row at which standard units begin */
    public static final int ROW_START_HUM = 6;
    /** the row at which temp units begin */
    public static final int ROW_START_TMP = 8;

    //---------------------------------------------------------------------
    //          INSTANCE DATA
    //---------------------------------------------------------------------
    /** this unit's designation per company guidelines */
    String locationDesignation;
    /** stores all units managed by this location */
    StorageUnit[][] units;
    /** maintains the customers for this location */
    Customer[] customers;
    /** the number of customers at this location (may be less than array size) */
    int customerCount;

    //---------------------------------------------------------------------
    //          CONSTRUCTORS
    //---------------------------------------------------------------------
    /**
     * StorageLocation Constructor
     *
     * @param   locationDesignation     the company's designation (name) for this location;
     *                                  must not be null or empty; must match the required
     *                                  format, two upper-case letters followed by two digits,
     *                                  followed by an additional string representing city
     */
    public StorageLocation(String locationDesignation) {
        if (locationDesignation == null || locationDesignation.isEmpty()) {
            throw new IllegalArgumentException("The location designation can't be empty or null");
        }
        if (!locationDesignation.matches("[A-Z]{2}[0-9]{2}[A-Za-z ]+")) {
            throw new IllegalArgumentException("Designation doesn't match required pattern");
        }
        this.locationDesignation = locationDesignation;
        customers = new Customer[NUM_CUSTOMERS];
        customerCount = 0;

        units = new StorageUnit[NUM_ROWS][NUM_SPACES];
        for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
            for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                if (rowIdx >= ROW_START_TMP) {
                    units[rowIdx][spaceIdx] = new StorageUnit(4, 8, 8, StorageUnit.UnitType.TEMPERATURE);
                } else if (rowIdx >= ROW_START_HUM) {
                    units[rowIdx][spaceIdx] = new StorageUnit(4, 8, 8, StorageUnit.UnitType.HUMIDITY);
                } else {
                    units[rowIdx][spaceIdx] = new StorageUnit(4, 8, 8, StorageUnit.UnitType.STANDARD);
                }
            }
        }
    }

    //---------------------------------------------------------------------
    //          ACCESSORS
    //---------------------------------------------------------------------
    
    /**
     * Retrieves the storage location's designation
     * 
     * @return      the location's designation
     */
    public String getDesignation() {
        return locationDesignation;
    }

    /**
     * Retrieves the number of rows in this location
     * 
     * @return      the number of rows
     */
    public int getRowCount() {
        return units.length;
    }

    /**
     * Retrieves the number of units in each row
     * 
     * @return      the number of units in a row
     */
    public int getUnitsPerRowCount() {
        return units[0].length;
    }
    
    /**
     * Retrieves a specific storage unit from the unit array
     *
     * @param   rowIdx      the row on which the unit sits
     * @param   spaceIdx    the space the unit occupies within the row
     * @return              the requested unit
     */
    public StorageUnit getStorageUnit(int rowIdx, int spaceIdx) {
        return units[rowIdx][spaceIdx];
    }

    /**
     * Retrieves a specific customer from the customer array
     *
     * @param   custIdx     the index of the desired customer
     * @return              the specified customer
     */
    public Customer getCustomer(int custIdx) {
        return customers[custIdx];
    }

    /**
     * Counts the number of customers currently in the array
     *
     * @return      the current customer count
     */
    public int getCustomerCount() {
        return customerCount;
    }

    //---------------------------------------------------------------------
    //          OTHER METHODS
    //---------------------------------------------------------------------
    /**
     * Adds a customer to the customer array
     *
     * @param   customer    the customer to add; must not be null
     * @return              the index at which the customer was added
     */
    public int addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer reference must not be null");
        }
        customers[customerCount] = customer;
        return customerCount++;
    }

    /**
     * Searches for units that are rented by a specific customer, returning them in an array
     *
     * @param       customer    the customer whose units are of interest; must not be null
     * @return                  an array of storage units belonging to that customer
     */
    public StorageUnit[] getCustomerUnits(Customer customer) {
        if (customer == null) {
            return null;
        }
        
        // Count the units
        int unitCount = 0;
        for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
            for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                if (units[rowIdx][spaceIdx].getCustomer() != null && units[rowIdx][spaceIdx].getCustomer() == customer) {
                    unitCount++;
                }
            }
        }

        // Create the array
        StorageUnit[] custUnits = new StorageUnit[unitCount];
        if (unitCount > 0) {
            // Fill the array
            int unitIdx = 0;
            for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
                for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                    if (units[rowIdx][spaceIdx].getCustomer() != null && units[rowIdx][spaceIdx].getCustomer() == customer) {
                        custUnits[unitIdx] = units[rowIdx][spaceIdx];
                        unitIdx++;
                    }
                }
            }
        }
        return custUnits;
    }

    /**
     * Returns an array of all available storage units
     *
     * @return      an array of available storage units
     */
    public StorageUnit[] getEmptyUnits() {
        return getEmptyUnits(null);
    }

    /**
     * Returns an array of all available storage units of the specified type
     *
     * @param   unitType    the type of units for which to search; pass null for wildcard (any type of unit)
     * @return              an array of available storage units of the specified type
     */
    public StorageUnit[] getEmptyUnits(StorageUnit.UnitType unitType) {
        // count the units
        int unitCount = 0;
        for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
            for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                if (unitType != null) {
                    // looking for a specific type of unit
                    if (units[rowIdx][spaceIdx].getType() == unitType && units[rowIdx][spaceIdx].getCustomer() == null) {
                        unitCount++;
                    }
                } else {
                    // no type of unit sought
                    if (units[rowIdx][spaceIdx].getCustomer() == null) {
                        unitCount++;
                    }
                }
            }
        }

        // create the array
        StorageUnit[] emptyUnits = new StorageUnit[unitCount];

        if (unitCount > 0) {
            int unitIdx = 0;
            for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
                for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                    if (unitType != null) {
                        // looking for a specific type of unit
                        if (units[rowIdx][spaceIdx].getType() == unitType && units[rowIdx][spaceIdx].getCustomer() == null) {
                            emptyUnits[unitIdx] = units[rowIdx][spaceIdx];
                            unitIdx++;
                        }
                    } else {
                        // no type of unit sought
                        if (units[rowIdx][spaceIdx].getCustomer() == null) {
                            emptyUnits[unitIdx] = units[rowIdx][spaceIdx];
                            unitIdx++;
                        }
                    }
                }
            }
        }

        // return the array
        return emptyUnits;
    }

    /**
     * Charges all customers their monthly rent
     *
     * @return      the total amount of rent charged to all customers
     */
    public double chargeMonthlyRent() {
        double totalRentCharged = 0.00;
        for (StorageUnit[] unitArray : units) {
            for (StorageUnit unit : unitArray) {
                if (unit.getCustomer() != null) {
                    Double unitRent = unit.getPrice();
                    unit.getCustomer().charge(unitRent);
                    totalRentCharged += unitRent;
                }
            }
        }
        return totalRentCharged;
    }

}
