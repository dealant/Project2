import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

/**
 * The test class StorageLocationTest.
 *
 * @author      Bill Barry
 * @version     2017-11-20
 */
public class StorageLocationTest {

    public static String DESIGNATION = "AZ23West Peoria";
    public static String CUST_NAME = "Joe Schmoe";
    public static String CUST_PHONE = "206-555-1212";
    public static double DOLLAR_VARIANCE = 0.001;
    
    private StorageLocation testLoc;
    private Customer testCust;
    
    /**
     * Default constructor for test class StorageLocationTest
     */
    public StorageLocationTest() {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        testLoc = new StorageLocation(DESIGNATION);    
        testCust = new Customer(CUST_NAME, CUST_PHONE);    
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {
    }

    //-----------------------------------------------------------------------
    //          HAPPY PATH TESTS    
    //-----------------------------------------------------------------------
    
    @Test
    public void testConstructor() {
        assertEquals(DESIGNATION, testLoc.getDesignation());
        assertEquals(0, testLoc.getCustomerCount());
    }
    
    @Test
    public void testAddCustomer() {
        testLoc.addCustomer(testCust);
        assertEquals(testCust, testLoc.getCustomer(0));
        assertEquals(1, testLoc.getCustomerCount());
    }

    @Test
    public void testGetCustomerUnits() {
        Customer cust2 = new Customer("Jane Doe", "425-555-1212");
        testLoc.addCustomer(cust2);
        StorageUnit[] custUnits = testLoc.getCustomerUnits(cust2);
        assertEquals(0, custUnits.length);
        
        testLoc.getStorageUnit(1, 1).rent(testCust, LocalDate.now(), 123.45);
        custUnits = testLoc.getCustomerUnits(testCust);
        assertEquals(1, custUnits.length);
        assertEquals(testLoc.getStorageUnit(1, 1), custUnits[0]);
        
        custUnits = testLoc.getCustomerUnits(null);
        assertNull(custUnits);
    }
    
    @Test
    public void testGetEmptyUnitsNoParams() {
        StorageUnit[] empties = testLoc.getEmptyUnits();
        assertEquals(testLoc.getRowCount() * testLoc.getUnitsPerRowCount(), empties.length);
        
        testLoc.getStorageUnit(1, 1).rent(testCust, LocalDate.now(), 0.01);
        empties = testLoc.getEmptyUnits();
        assertEquals(testLoc.getRowCount() * testLoc.getUnitsPerRowCount() - 1, empties.length);
        for (StorageUnit unit : empties) {
            assertNull(unit.getCustomer());
        }

        testLoc.getStorageUnit(2, 1).rent(testCust, LocalDate.now(), 0.01);
        empties = testLoc.getEmptyUnits();
        assertEquals(testLoc.getRowCount() * testLoc.getUnitsPerRowCount() - 2, empties.length);
        for (StorageUnit unit : empties) {
            assertNull(unit.getCustomer());
        }
    }

    @Test
    public void testGetEmptyUnitsTypes() {
        testLoc.getStorageUnit(1, 1).rent(testCust, LocalDate.now(), 0.01);
        
        StorageUnit[] empties = testLoc.getEmptyUnits(StorageUnit.UnitType.STANDARD);
        assertEquals(6 * 20 - 1, empties.length);
        for (StorageUnit unit : empties) {
            assertEquals(StorageUnit.UnitType.STANDARD, unit.getType());
            assertNull(unit.getCustomer());
        }
        empties = testLoc.getEmptyUnits(StorageUnit.UnitType.HUMIDITY);
        assertEquals(2 * 20, empties.length);
        for (StorageUnit unit : empties) {
            assertEquals(StorageUnit.UnitType.HUMIDITY, unit.getType());
            assertNull(unit.getCustomer());
        }
        empties = testLoc.getEmptyUnits(StorageUnit.UnitType.TEMPERATURE);
        assertEquals(4 * 20, empties.length);
        for (StorageUnit unit : empties) {
            assertEquals(StorageUnit.UnitType.TEMPERATURE, unit.getType());
            assertNull(unit.getCustomer());
        }
    }
    
    @Test
    public void testChargeMonthlyRent() {
        Customer cust2 = new Customer("Jane Doe", "206-555-1234");
        testLoc.addCustomer(testCust);
        testLoc.addCustomer(cust2);
        
        assertEquals(0.00, testLoc.chargeMonthlyRent(), DOLLAR_VARIANCE);
        testLoc.getStorageUnit(1, 1).rent(testCust, LocalDate.now(), 111.11);
        testLoc.getStorageUnit(1, 2).rent(testCust, LocalDate.now(), 222.22);
        testLoc.getStorageUnit(2, 1).rent(cust2,    LocalDate.now(), 333.33);
        assertEquals(111.11 + 222.22 + 333.33, testLoc.chargeMonthlyRent(), DOLLAR_VARIANCE);
        assertEquals(111.11 + 222.22, testLoc.getCustomer(0).getBalance(),  DOLLAR_VARIANCE);
        assertEquals(333.33,          testLoc.getCustomer(1).getBalance(),  DOLLAR_VARIANCE);
    }
    
    
    //-----------------------------------------------------------------------
    //          PRECONDITION TESTS    
    //-----------------------------------------------------------------------
    
    @Test (expected = IllegalArgumentException.class) 
    public void testConstrStateLower() {
        StorageLocation testLoc = new StorageLocation("wa23Issaquah");
    }

    @Test (expected = IllegalArgumentException.class) 
    public void testConstrStateOuterBoundaries() {
        StorageLocation testLoc = new StorageLocation("@[23Issaquah");
    }

    @Test (expected = IllegalArgumentException.class) 
    public void testConstrStateNum() {
        StorageLocation testLoc = new StorageLocation("9923Issaquah");
    }

    @Test (expected = IllegalArgumentException.class) 
    public void testConstrNumAlpha() {
        StorageLocation testLoc = new StorageLocation("WAwaIssaquah");
    }
    
    @Test (expected = IllegalArgumentException.class) 
    public void testConstrNumOuterBoundaries() {
        StorageLocation testLoc = new StorageLocation("WA/:Issaquah");
    }
    
    @Test (expected = IllegalArgumentException.class) 
    public void testConstrCityNone() {
        StorageLocation testLoc = new StorageLocation("WA23");
    }
    
}
