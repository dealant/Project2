import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Customer class
 *
 * @author      Bill Barry
 * @version     2017-11-20
 */
public class CustomerTest {
    /**
     * Default constructor for test class CustomerTest
     */
    public CustomerTest() {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {
    }

    //--------------------------------------------------------------
    //      General Tests
    //--------------------------------------------------------------
    
    @Test 
    public void testConstructorAndGets() {
        Customer testCust = new Customer("Pat Smith", "206-555-1212");
        assertEquals("Pat Smith",       testCust.getName());
        assertEquals("206-555-1212",    testCust.getPhone());
        assertEquals(0.0,               testCust.getBalance(), 0.001);
    }
    
    @Test
    public void testSets() {
        Customer testCust = new Customer("Lupe Saenz", "425-555-1212");
        testCust.setName("Lupe Sanchez");
        testCust.setPhone("206-555-1212");
        assertEquals("Lupe Sanchez",    testCust.getName());
        assertEquals("206-555-1212",    testCust.getPhone());
    }
    
    @Test
    public void testChargeAndCredit() {
        Customer testCust = new Customer("Dana Danzig", "915-555-1212");
        assertEquals(123.45, testCust.charge(123.45),   0.001);
        testCust.charge(0.01);
        assertEquals(123.46, testCust.getBalance(),     0.001);
        assertEquals(64.11, testCust.credit(59.35),     0.001);
        assertEquals(64.11, testCust.getBalance(),      0.001);
    }

    //--------------------------------------------------------------
    //      Precondition Tests
    //--------------------------------------------------------------

    @Test (expected = IllegalArgumentException.class)
    public void testConstrNameNull() {
        Customer testCust = new Customer(null, "999-999-9999");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrNameEmpty() {
        Customer testCust = new Customer("", "999-999-9999");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrPhoneNull() {
        Customer testCust = new Customer("Chris Campos", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrPhoneEmpty() {
        Customer testCust = new Customer("Chris Campos", "");
    }
}
