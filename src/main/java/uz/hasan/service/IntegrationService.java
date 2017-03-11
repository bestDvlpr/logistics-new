package uz.hasan.service;

import uz.hasan.service.dto.IntegrateDTO;

import java.util.List;

public interface IntegrationService {

    Boolean integrate(List<IntegrateDTO> integrateDTOS);


}
