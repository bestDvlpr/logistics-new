package uz.multimafe.service;

import uz.multimafe.domain.Receipt;

import java.util.List;

public interface IntegrationService {

    void integrate(List<Receipt> receiptList);

}
