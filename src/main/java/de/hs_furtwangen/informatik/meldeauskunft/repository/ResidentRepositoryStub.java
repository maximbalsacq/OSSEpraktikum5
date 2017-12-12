package de.hs_furtwangen.informatik.meldeauskunft.repository;

import de.hs_furtwangen.informatik.meldeauskunft.domain.*;

import java.util.*;

public class ResidentRepositoryStub implements ResidentRepository {
	public List getResidents() {
		return Arrays.asList(
				new Resident("Ada", "Alice", "Adlerstraße", "Althausen", new Date(0, 0, 1)),
				new Resident("Bob", "Braun", "Billigweg", "Brüssel", new Date(1, 1, 2))
		);
	}
}
