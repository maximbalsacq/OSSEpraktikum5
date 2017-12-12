package de.hs_furtwangen.informatik.meldeauskunft;
import de.hs_furtwangen.informatik.meldeauskunft.service.*;
import de.hs_furtwangen.informatik.meldeauskunft.domain.*;
import de.hs_furtwangen.informatik.meldeauskunft.repository.*;


import org.junit.Test; 
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List; 

public class ResidentServiceTest {
	/*
	 * Test if getting a nonexistent resident causes an Exception
	 */
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
	
	/*
	 * Check if an Exception is thrown while an attempt is made get a unique resident
	 * while the resident contains wildcards
	 */
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
	
	/*
	 * Check if getUniqueResident() works when passed a Resident (all properties must be the same)
	 */
	@Test
	public void testgetResidentWorking() throws ResidentServiceException {
		BaseResidentService s = new BaseResidentService();
		s.setResidentRepository(new ResidentRepositoryStub());
		
		Resident expectedresult = new Resident("Ada", "Alice", "Adlerstraße", "Althausen", new Date(0, 0, 1));
		Resident actualresult = s.getUniqueResident(expectedresult);
		
		assertEquals(true, areResidentsEqual(expectedresult, actualresult));
	}
	
	/*
	 * Utility compraison function as Resident does not have an equals() method
	 */
	private static boolean areResidentsEqual(Resident expectedresult, Resident actualresult) {
		return (
				expectedresult.getGivenName().equals(actualresult.getGivenName()) &&
				expectedresult.getFamilyName().equals(actualresult.getFamilyName()) &&
				expectedresult.getStreet().equals(actualresult.getStreet()) &&
				expectedresult.getCity().equals(actualresult.getCity()) &&
				expectedresult.getDateOfBirth().equals(actualresult.getDateOfBirth())
		);
	}
	
	/*
	 * Checks if all residents returned by calling getFilteredResidentsList()
	 * with wildcards exist and none are missing (result set must be equal
	 * to test set).
	 */
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
	
	/*
	 * Test if an empty list is returned when nothing matches
	 */
	@Test
	public void testgetAllFilteredNothingMatches() throws ResidentServiceException {
		BaseResidentService s = new BaseResidentService();
		s.setResidentRepository(new ResidentRepositoryStub());
		
		assertEquals(0, s.getFilteredResidentsList(new Resident("clara", "cool", "c", "c", null)).size());
	}
	
	/*
	 * Test if Residents are correctly filtered using wildcards
	 */
	@Test
	public void testgetAllFilteredWildcard() {
		BaseResidentService s = new BaseResidentService();
		s.setResidentRepository(new ResidentRepositoryStub());
		
		Resident search = new Resident("Ada", "*", "*", "*", null);
		Resident expectedresult = new Resident("Ada", "Alice", "Adlerstraße", "Althausen", new Date(0, 0, 1));
		List<Resident> results  = s.getFilteredResidentsList(search);
		
		assertEquals(1, results.size());
		Resident actualresult = results.get(0);
		assertEquals(true, areResidentsEqual(expectedresult, actualresult));
	}
}
