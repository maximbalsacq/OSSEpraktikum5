package de.balsacq;
import org.junit.Test; 
import static org.junit.Assert.*; 

public class UtilTest {
	@Test(expected=IllegalArgumentException.class)
	public void testIstErstesHalbjahrArgOOBLow() {
		Util.istErstesHalbjahr(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIstErstesHalbjahrArgOOBHigh() {
		Util.istErstesHalbjahr(13);
	}
	
	@Test
	public void testIstErstesHalbjahr() {
		assertEquals(true, Util.istErstesHalbjahr(1));
		assertEquals(true, Util.istErstesHalbjahr(6));
		assertEquals(false, Util.istErstesHalbjahr(7));
	}
}