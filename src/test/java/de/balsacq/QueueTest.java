package de.balsacq;
import org.junit.Test; 
import static org.junit.Assert.*; 

public class QueueTest {
	/**
	 * Testet, ob ein einfaches einfuegen funktioniert
	 */
	@Test
	public void testQueueSingleEnqueueWorking() {
		Queue q = new Queue(3);
		int x = 1;
		q.enqueue(x);
		assertEquals(q.dequeue(), x);
	}
	
	/**
	 * Testet, ob mehrfaches einfuegen und ueberschreiben korrekt funktioniert
	 */
	@Test
	public void testQueueMultipleInsertWorking() {
		Queue q = new Queue(3);
		q.enqueue(0);
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3); // Sollte den letzten Eintrag überschreiben!
		assertEquals(0, q.dequeue());
		assertEquals(1, q.dequeue());
		assertEquals(3, q.dequeue());
	}
	
	/**
	 * Testet, ob eine Queue mit einer Größe von 0 eine Exception generiert
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testQueueZeroSize() {
		Queue q = new Queue(0);
	}
	
	/**
	 * Testet, ob eine Queue mit einer Größe von -1 eine Exception generiert
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testQueueNegativeSize() {
		Queue q = new Queue(-1);
	}
}