package de.hs_furtwangen.informatik.meldeauskunft;
import de.hs_furtwangen.informatik.meldeauskunft.service.*;
import de.hs_furtwangen.informatik.meldeauskunft.domain.*;
import de.hs_furtwangen.informatik.meldeauskunft.repository.*;


import org.junit.Test; 
import static org.junit.Assert.*; 

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
}
