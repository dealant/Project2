import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class StorageUnitTest.
 *
 * @author      Bill Barry
 * @version     2017-11-20
 */
public class StorageUnitTest {

    /**
     * Default constructor for test class StorageUnitTest
     */
    public StorageUnitTest() {
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
    public void testConstrAndGets() {
        StorageUnit testUnit = new StorageUnit(4, 8, 10, StorageUnit.UnitType.STANDARD);
        assertEquals( 4,    testUnit.getWidth());
        assertEquals( 8,    testUnit.getLength());
        assertEquals(10,    testUnit.getHeight());
        assertEquals(StorageUnit.UnitType.STANDARD, testUnit.getType());
    }
    
    @Test
    public void testRentAndRelease() {
        StorageUnit testUnit = new StorageUnit(4, 8, 10, StorageUnit.UnitType.HUMIDITY);        
        Customer testCust = new Customer("Reagan Rochester", "206-555-1212");
        LocalDate testDate = LocalDate.of(2018, 01, 01);
        // Rent
        testUnit.rent(testCust, testDate, 150.75);
        assertEquals(testCust,  testUnit.getCustomer());
        assertEquals(150.75,    testUnit.getPrice(),        0.001);
        assertEquals(testDate,  testUnit.getRentalStart());
        // Release
        testUnit.release();
        assertEquals(null,      testUnit.getCustomer());
        assertEquals(0.0,       testUnit.getPrice(),        0.001);
        assertEquals(null,      testUnit.getRentalStart());
        
    }
    
    
    //--------------------------------------------------------------
    //      Precondition Tests
    //--------------------------------------------------------------

    @Test (expected = IllegalArgumentException.class)
    public void testConstrWidthLowerBounds() {
        StorageUnit testUnit = new StorageUnit(0, 8, 8, StorageUnit.UnitType.STANDARD);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrLengthLowerBounds() {
        StorageUnit testUnit = new StorageUnit(8, 0, 8, StorageUnit.UnitType.STANDARD);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrHeightLowerBounds() {
        StorageUnit testUnit = new StorageUnit(8, 8, 0, StorageUnit.UnitType.STANDARD);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrWidthMult4() {
        StorageUnit testUnit = new StorageUnit(6, 8, 8, StorageUnit.UnitType.STANDARD);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrLengthMult4() {
        StorageUnit testUnit = new StorageUnit(8, 6, 8, StorageUnit.UnitType.STANDARD);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrHeightMult2() {
        StorageUnit testUnit = new StorageUnit(8, 8, 3, StorageUnit.UnitType.STANDARD);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrUnitTypeNull() {
        StorageUnit testUnit = new StorageUnit(8, 8, 8, null);
    }

}
