package de.hs_furtwangen.informatik.meldeauskunft;
import de.hs_furtwangen.informatik.meldeauskunft.service.*;
import de.hs_furtwangen.informatik.meldeauskunft.domain.*;
import de.hs_furtwangen.informatik.meldeauskunft.repository.*;


import org.junit.Test; 
import static org.junit.Assert.*;

import java.util.Date; 

public class ResidentServiceTest {
	@Test
	public void testgetNonexistentResident() throws ResidentServiceException {
		BaseResidentService s = new BaseResidentService();
		s.setResidentRepository(new ResidentRepositoryStub());
		try {
			s.getUniqueResident(new Resident("clara", "cool", "c", "c", null));
		} catch(ResidentServiceException e) {
			assertEquals("Suchanfrage lieferte kein eindeutiges Ergebnis!", e.getMessage());
		}
	}
	
	@Test
	public void testgetResidentWithWildcard() {
		BaseResidentService s = new BaseResidentService();
		s.setResidentRepository(new ResidentRepositoryStub());
		try {
			s.getUniqueResident(new Resident("clara*", "cool", "c", "c", null));
		} catch(ResidentServiceException e) {
			assertEquals("Wildcards (*) sind nicht erlaubt!", e.getMessage());
		}
	}
	
	@Test
	public void testgetResidentWorking() throws ResidentServiceException {
		BaseResidentService s = new BaseResidentService();
		s.setResidentRepository(new ResidentRepositoryStub());
		
		Resident expectedresult = new Resident("Ada", "Alice", "Adlerstraße", "Althausen", new Date(0, 0, 1));
		Resident actualresult = s.getUniqueResident(expectedresult);
		
		// We have no Resident.equals() method => have to check with JUnit
		assertEquals(expectedresult.getGivenName(), actualresult.getGivenName());
		assertEquals(expectedresult.getFamilyName(), actualresult.getFamilyName());
		assertEquals(expectedresult.getStreet(), actualresult.getStreet());
		assertEquals(expectedresult.getCity(), actualresult.getCity());
	}
}
