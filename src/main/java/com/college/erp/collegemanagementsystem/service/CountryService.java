package com.college.erp.collegemanagementsystem.service;

import java.util.List;
import com.college.erp.collegemanagementsystem.dto.CountryDTO;
import com.college.erp.collegemanagementsystem.dto.PagablePage;
import com.college.erp.collegemanagementsystem.dto.request.CountryCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.CountryUpdateRequest;

/**
 * @author grish
 *
 */
public interface CountryService {

    CountryDTO createCountry(CountryCreateRequest request);

    CountryDTO updateCountry(Long id, CountryUpdateRequest request);

    CountryDTO getCountryById(Long id);

    List<CountryDTO> getAllCountries();

    PagablePage<CountryDTO> getCountriesPage(String search, Integer page, Integer size);

    CountryDTO deleteCountry(Long id);
}
