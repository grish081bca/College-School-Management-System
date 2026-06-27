package com.college.erp.collegemanagementsystem.validation;

import com.college.erp.collegemanagementsystem.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.college.erp.collegemanagementsystem.exception.ResourceNotFoundException;
import com.college.erp.collegemanagementsystem.entity.City;
import com.college.erp.collegemanagementsystem.entity.Country;
import com.college.erp.collegemanagementsystem.entity.State;
import com.college.erp.collegemanagementsystem.repository.CityRepository;
import com.college.erp.collegemanagementsystem.repository.CountryRepository;
import com.college.erp.collegemanagementsystem.repository.StateRepository;

import java.util.Optional;

/**
 * @author grish
 */

@Service
@Transactional(readOnly = true)
public class LocationValidationService {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public LocationValidationService(CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    public Country getCountry(Long id) {
        return countryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country not found."));
    }

    public State getState(Long id, Long countryId) {
        State state = stateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("State not found."));
        Country country = countryRepository.findCountryById(countryId);
        if (country == null) {
            throw new ResourceNotFoundException("Country not found.");
        }
        if (!state.getCountry().getId().equals(countryId)) {
            throw new ValidationException("Selected State does not belong to country " + country.getName());
        }
        return state;
    }

    public City getCity(Long id, Long stateId) {
        City city = cityRepository.findCityById(id);
        State state = stateRepository.findStateById(stateId);
        if (city == null) {
            throw new ResourceNotFoundException("City not found.");
        }
        if (state == null) {
            throw new ResourceNotFoundException("State not found.");
        }
        if (!city.getState().getId().equals(stateId)) {
            throw new ResourceNotFoundException("City does not belong to state " + state.getName());
        }
        return city;
    }
}
