import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * JUnit test fixture for testing PizzaOrderManager implemented methods.
 */
public class PizzaOrderManagerTest {

    /**
     * Tests getPriceMap with a short custom valid price map file.
     */
    @Test
    public void getPriceMapTestStd1() {
        SimpleWriter sw = new SimpleWriter1L("data/test.txt");
        sw.println("tiny,100");
        sw.println("small,500");
        sw.println("pico,800");

        Map<String, Integer> resTest = new Map1L<>();
        Map<String, Integer> resExpected = resTest.newInstance();
        resExpected.add("tiny", 100);
        resExpected.add("small", 500);
        resExpected.add("pico", 800);

        PizzaOrderManager.getPriceMap("data/test.txt", resTest);

        assertEquals(resExpected, resTest);

        sw.close();
    }

    /**
     * Tests getPriceMap with the preset "sizes.txt" price map file.
     */
    @Test
    public void getPriceMapTestSizes() {
        Map<String, Integer> resTest = new Map1L<>();
        Map<String, Integer> resExpected = resTest.newInstance();
        resExpected.add("small", 795);
        resExpected.add("medium", 995);
        resExpected.add("large", 1295);
        resExpected.add("biggie", 1595);
        resExpected.add("great biggie", 1995);

        PizzaOrderManager.getPriceMap("data/sizes.txt", resTest);

        assertEquals(resExpected, resTest);
    }

    /**
     * Tests getOneOrder with a standard custom order in "test.txt".
     */
    @Test
    public void getOneOrderTestStd1() {
        Map<String, Integer> smap = new Map1L<>();
        Map<String, Integer> tmap = smap.newInstance();

        PizzaOrderManager.getPriceMap("data/sizes.txt", smap);
        PizzaOrderManager.getPriceMap("data/toppings.txt", tmap);

        SimpleWriter sw = new SimpleWriter1L("data/test.txt");
        sw.println("biggie");
        sw.println("anchovy");
        sw.println("anchovy");
        sw.println("anchovy");
        sw.println("onion");
        sw.println();

        SimpleReader sr = new SimpleReader1L("data/test.txt");

        assertEquals(PizzaOrderManager.getOneOrder(sr, smap, tmap), 2140);

        sw.close();
        sr.close();
    }

    /**
     * Tests getOneOrder with a standard custom order in "test.txt".
     */
    @Test
    public void getOneOrderTestStd2() {
        Map<String, Integer> smap = new Map1L<>();
        Map<String, Integer> tmap = smap.newInstance();

        PizzaOrderManager.getPriceMap("data/sizes.txt", smap);
        PizzaOrderManager.getPriceMap("data/toppings.txt", tmap);

        SimpleWriter sw = new SimpleWriter1L("data/test.txt");
        sw.println("great biggie");
        sw.println("pineapple");
        sw.println("sausage");
        sw.println();

        SimpleReader sr = new SimpleReader1L("data/test.txt");

        assertEquals(PizzaOrderManager.getOneOrder(sr, smap, tmap), 2210);

        sw.close();
        sr.close();
    }

    /**
     * Tests putPriceTest with prices that are >1 satisfying cents >= 10.
     */
    @Test
    public void putPriceTestStdGreaterThanOneDollar() {
        SimpleWriter sw = new SimpleWriter1L("data/test.txt");
        SimpleReader sr = new SimpleReader1L("data/test.txt");
        PizzaOrderManager.putPrice(sw, 1253);
        PizzaOrderManager.putPrice(sw, 935);

        assertEquals(sr.nextLine(), "$12.53");
        assertEquals(sr.nextLine(), "$9.35");

        sw.close();
        sr.close();
    }

    /**
     * Tests putPriceTest with prices that are >1 and contain zeroes (cents <
     * 10).
     */
    @Test
    public void putPriceTestZeroedGreaterThanOneDollar() {
        SimpleWriter sw = new SimpleWriter1L("data/test.txt");
        SimpleReader sr = new SimpleReader1L("data/test.txt");
        PizzaOrderManager.putPrice(sw, 105);
        PizzaOrderManager.putPrice(sw, 305);

        assertEquals(sr.nextLine(), "$1.05");
        assertEquals(sr.nextLine(), "$3.05");

        sw.close();
        sr.close();
    }

    /**
     * Tests putPriceTest with prices that are <1 and contain zeroes (cents <
     * 10).
     */
    @Test
    public void putPriceTestZeroedLessThanOneDollar() {
        SimpleWriter sw = new SimpleWriter1L("data/test.txt");
        SimpleReader sr = new SimpleReader1L("data/test.txt");
        PizzaOrderManager.putPrice(sw, 30);
        PizzaOrderManager.putPrice(sw, 10);

        assertEquals(sr.nextLine(), "$0.30");
        assertEquals(sr.nextLine(), "$0.10");

        sw.close();
        sr.close();
    }
}
