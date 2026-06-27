package com.college.erp.collegemanagementsystem.service;

import java.util.List;
import com.college.erp.collegemanagementsystem.dto.CityDTO;
import com.college.erp.collegemanagementsystem.dto.request.CityCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.CityUpdateRequest;

/**
 * @author grish
 */

public interface CityService {

    CityDTO createCity(CityCreateRequest request);

    CityDTO updateCity(Long id, CityUpdateRequest request);

    CityDTO getCityById(Long id);

    List<CityDTO> getAllCities();

    List<CityDTO> getCitiesByStateId(Long stateId);

    CityDTO deleteCity(Long id);
}
