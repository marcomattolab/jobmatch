package it.aranciaict.jobmatch.domain.comparators;

import java.util.Comparator;

import it.aranciaict.jobmatch.service.dto.CompanySectorDTO;

public class CompanySectorComparator implements Comparator<CompanySectorDTO> {

	@Override
	public int compare(CompanySectorDTO o1, CompanySectorDTO o2) {
		return o1.getDescription().compareTo(o2.getDescription());
	}

}
