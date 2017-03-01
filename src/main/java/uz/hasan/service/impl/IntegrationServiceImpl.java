package uz.hasan.service.impl;

import uz.hasan.domain.Receipt;
import uz.hasan.repository.ReceiptRepository;
import uz.hasan.service.IntegrationService;
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
