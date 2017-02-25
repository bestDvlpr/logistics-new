package uz.multimafe.service.impl;

import uz.multimafe.domain.Receipt;
import uz.multimafe.repository.ReceiptRepository;
import uz.multimafe.service.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class IntegrationServiceImpl implements IntegrationService {

    private final Logger log = LoggerFactory.getLogger(IntegrationServiceImpl.class);


    @Inject
    private ReceiptRepository receiptRepository;



    @Override
    public void integrate(List<Receipt> receipts) {

    }
}
