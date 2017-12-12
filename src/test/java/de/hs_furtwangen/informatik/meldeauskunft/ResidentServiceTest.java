package de.hs_furtwangen.informatik.meldeauskunft;
import de.hs_furtwangen.informatik.meldeauskunft.service.*;
import de.hs_furtwangen.informatik.meldeauskunft.domain.*;
import de.hs_furtwangen.informatik.meldeauskunft.repository.*;


import org.junit.Test; 
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List; 

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
		
		assertEquals(true, areResidentsEqual(expectedresult, actualresult));
	}
	
	private static boolean areResidentsEqual(Resident expectedresult, Resident actualresult) {
		return (
				expectedresult.getGivenName().equals(actualresult.getGivenName()) &&
				expectedresult.getFamilyName().equals(actualresult.getFamilyName()) &&
				expectedresult.getStreet().equals(actualresult.getStreet()) &&
				expectedresult.getCity().equals(actualresult.getCity()) &&
				expectedresult.getDateOfBirth().equals(actualresult.getDateOfBirth())
		);
	}
	
	@Test
	public void testgetAllFilteredListall() {
		BaseResidentService s = new BaseResidentService();
		s.setResidentRepository(new ResidentRepositoryStub());
		
		List<Resident> alltestresidents = new ResidentRepositoryStub().getResidents();
		List<Resident> allresidents = s.getFilteredResidentsList(new Resident("*", "*", "*", "*", null));
		
		assertEquals(alltestresidents.size(), allresidents.size());
		for(Resident t: alltestresidents) {
			boolean isexistingresident = false;
			for(Resident r : allresidents) {
				if(areResidentsEqual(t, r)) {
					isexistingresident = true;
					break;
				}
			}
			assertEquals(true, isexistingresident);
		}
	}
}
