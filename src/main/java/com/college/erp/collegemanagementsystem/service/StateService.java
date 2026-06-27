package com.college.erp.collegemanagementsystem.service;

import java.util.List;
import com.college.erp.collegemanagementsystem.dto.StateDTO;
import com.college.erp.collegemanagementsystem.dto.request.StateCreateRequest;
import com.college.erp.collegemanagementsystem.dto.request.StateUpdateRequest;

/**
 * @author grish
 */

public interface StateService {

    StateDTO createState(StateCreateRequest request);

    StateDTO updateState(Long id, StateUpdateRequest request);

    StateDTO getStateById(Long id);

    List<StateDTO> getAllStates();

    List<StateDTO> getStatesByCountryId(Long countryId);

    StateDTO deleteState(Long id);
}
