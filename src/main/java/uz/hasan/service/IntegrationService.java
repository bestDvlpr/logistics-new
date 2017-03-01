package uz.hasan.service;

import uz.hasan.domain.Receipt;

import java.util.List;

public interface IntegrationService {

    void integrate(List<Receipt> receiptList);

}
