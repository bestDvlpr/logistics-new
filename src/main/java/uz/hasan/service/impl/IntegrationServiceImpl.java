package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.domain.enumeration.*;
import uz.hasan.repository.*;
import uz.hasan.service.IntegrationService;
import uz.hasan.service.dto.ClientDTO;
import uz.hasan.service.dto.IntegrateDTO;
import uz.hasan.service.dto.PaymentIntegrate;
import uz.hasan.service.dto.ProductIntegrate;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class IntegrationServiceImpl implements IntegrationService {

    private final Logger log = LoggerFactory.getLogger(IntegrationServiceImpl.class);


    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private PayMasterRepository payMasterRepository;

    @Inject
    private LoyaltyCardRepository loyaltyCardRepository;

    @Inject
    private ProductEntryRepository productEntryRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private SellerRepository sellerRepository;

    @Inject
    private PayTypeRepository payTypeRepository;

    @Inject
    private ShopRepository shopRepository;
    @Inject
    private CompanyRepository companyRepository;

    @Override
    public Boolean integrate(List<IntegrateDTO> integrateDTOS) {
        for (IntegrateDTO integrateDTO : integrateDTOS) {
            Receipt receipt = receiptRepository.findFirstByDocID(integrateDTO.getDocID());
            try {
                createOrUpdateReceipt(receipt, integrateDTO);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }


    private void createOrUpdateReceipt(Receipt receipt, IntegrateDTO integrateDTO) throws ValidationException {

        if (receipt == null)
            receipt = new Receipt();
        /* create date without time to compare old receipts*/
        LocalDate localDate = LocalDate.now();
//        Date today = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        /*if (integrateDTO.getProducts().isEmpty() || new Date(integrateDTO.getDocDate()).before(today)) {
            return;
        }*/

        boolean anyMatch = integrateDTO.getProducts().stream().anyMatch(productIntegrate -> productIntegrate.getDeliveryFlag().equals(String.valueOf(SalesType.DELIVERY.ordinal())));
        if (!anyMatch) {
            return;
        }

        DocType docType = DocType.get(integrateDTO.getDocType());

        WholeSaleFlag wholeSaleFlag = WholeSaleFlag.get(integrateDTO.getWholesaleFlag());

        PayMaster master = getPayMaster(integrateDTO);

        LoyaltyCard loyaltyCard = getLoyaltyCard(integrateDTO);

        if (integrateDTO.getDocDate() == null)
            throw new NullPointerException("Doc Date is null");

        receipt.setDocDate(integrateDTO.getDocDate());
        receipt.setDocID(integrateDTO.getDocID());
        receipt.setDocNum(integrateDTO.getDocNum());
        receipt.setPreviousDocID(integrateDTO.getPreviousDocID());
        receipt.setPayMaster(master);
        receipt.setLoyaltyCard(loyaltyCard);
        receipt.setWholeSaleFlag(wholeSaleFlag);
        receipt.setDocType(docType);
//        if (receipt.getStatus() == null)
        receipt.setStatus(ReceiptStatus.NEW);

        receipt.setCompany(companyRepository.findByIdNumber(integrateDTO.getShopId()));
        receipt = receiptRepository.save(receipt);

        updateOrCreateProductEntries(receipt, integrateDTO.getProducts());

        updateOrCreatePayTypes(receipt, integrateDTO.getPayments());

        updateOrCreateClient(receipt, integrateDTO.getClient());


    }


    private void updateOrCreateClient(Receipt savedReceipt, ClientDTO client) {


    }

    private void updateOrCreateProductEntries(Receipt savedReceipt, List<ProductIntegrate> productIntegrates) throws ValidationException {
        Set<ProductEntry> productEntries = savedReceipt.getProductEntries();
        log.debug("Products found: " + String.valueOf(productEntries.size()));

        for (ProductIntegrate productIntegrate : productIntegrates) {
            if (SalesType.DELIVERY == SalesType.get(productIntegrate.getDeliveryFlag())) {

                for (int qty = 0; qty < productIntegrate.getQty().intValue(); qty++) {


                    boolean hasEntry = false;

                    for (ProductEntry productEntry : productEntries) {

                        if (productIntegrate.eq(productEntry)) {
                            if (savedReceipt.getDocType() == DocType.RETURN)
                                productEntry.setCancelled(true);
                            else
                                productEntry.setCancelled(false);

                            productEntryRepository.save(productEntry);
                            hasEntry = true;

                        }
                    }

                    if (!hasEntry) {

                        SalesType salesType = SalesType.get(productIntegrate.getDeliveryFlag());

                        SalesPlace salesPlace = SalesPlace.get(productIntegrate.getHallFlag());

                        DefectFlag defectFlag = DefectFlag.get(productIntegrate.getDefectFlag());

                        VirtualFlag virtualFlag = VirtualFlag.get(productIntegrate.getVirtualFlag());

                        uz.hasan.domain.Product p = getProduct(productIntegrate);

                        Seller seller = getSeller(productIntegrate);


                        ProductEntry productEntry = new ProductEntry();
                        productEntry.setQty(productIntegrate.getQty());
                        productEntry.setComment(productIntegrate.getComment());
                        productEntry.setDiscount(productIntegrate.getDiscount());
                        productEntry.setPrice(productIntegrate.getPrice());
                        productEntry.setReason(productIntegrate.getReason());
                        productEntry.setGuid(productIntegrate.getGuid());
                        productEntry.setStatus(ReceiptStatus.NEW);
                        productEntry.setDeliveryFlag(salesType);
                        productEntry.setHallFlag(salesPlace);
                        productEntry.setDefectFlag(defectFlag);
                        productEntry.setVirtualFlag(virtualFlag);
                        productEntry.setProduct(p);
                        productEntry.setSellerID(seller);
                        productEntry.setReceipt(savedReceipt);
//                        productEntry.setCompany(savedReceipt.getCompany());

                        if (savedReceipt.getDocType() == DocType.RETURN)
                            productEntry.setCancelled(true);
                        else
                            productEntry.setCancelled(false);


                        productEntryRepository.save(productEntry);
                    }

                }
            }
        }


    }

    private void updateOrCreatePayTypes(Receipt savedReceipt, List<PaymentIntegrate> paymentIntegrates) {
        Set<PayType> payTypes = savedReceipt.getPayTypes();

        if (payTypes != null && !payTypes.isEmpty()) {
            log.debug("Payments found: " + String.valueOf(payTypes.size()));

            for (PayType payType : payTypes) {
                payTypeRepository.delete(payType);
            }
        }

        for (PaymentIntegrate paymentIntegrate : paymentIntegrates) {

            try {
                PaymentType paymentType = PaymentType.get(paymentIntegrate.getType());

                PayType payType = new PayType();
                payType.setReceipt(savedReceipt);
                payType.setAmount(paymentIntegrate.getAmount());
                payType.setSerial(paymentIntegrate.getSerial());
                payType.setSapCode(paymentIntegrate.getSapCode());

                payType.setPaymentType(paymentType);

                payTypeRepository.save(payType);


            } catch (ValidationException e) {
                e.printStackTrace();
            }

        }
    }

    private PayMaster getPayMaster(IntegrateDTO integrateDTO) {
        if (integrateDTO == null ||
            integrateDTO.getPaymasterID() == null ||
            integrateDTO.getPaymasterID().isEmpty())
            return null;

        PayMaster payMaster = payMasterRepository.findByPaymasterID(integrateDTO.getPaymasterID());

        if (payMaster == null) {
            PayMaster master = new PayMaster();
            master.setPayMasterName(integrateDTO.getPayMasterName());
            master.setPaymasterID(integrateDTO.getPaymasterID());
            payMaster = payMasterRepository.save(master);
        }

        return payMaster;
    }

    private LoyaltyCard getLoyaltyCard(IntegrateDTO integrateDTO) {
        if (integrateDTO == null ||
            integrateDTO.getLoyaltyCardID() == null ||
            integrateDTO.getLoyaltyCardID().isEmpty())
            return null;

        LoyaltyCard loyaltyCard = loyaltyCardRepository.findFirstByLoyaltyCardID(integrateDTO.getLoyaltyCardID());

        if (loyaltyCard == null) {
            LoyaltyCard card = new LoyaltyCard();
            card.setLoyaltyCardID(integrateDTO.getLoyaltyCardID());
            card.setLoyaltyCardAmount(integrateDTO.getLoyaltyCardAmount());
            //card.setLoyaltyCardBonus(integrateDTO.getLoyaltyCardBonus());
            loyaltyCard = loyaltyCardRepository.save(card);
        }

        return loyaltyCard;
    }

    private uz.hasan.domain.Product getProduct(ProductIntegrate integrateProductIntegrate) {
        if (integrateProductIntegrate == null ||
            integrateProductIntegrate.getSapCode() == null ||
            integrateProductIntegrate.getSapCode().isEmpty())
            return null;

        uz.hasan.domain.Product product = productRepository.findFirstBySapCode(integrateProductIntegrate.getSapCode());
        if (product == null) {
            uz.hasan.domain.Product p = new uz.hasan.domain.Product();
            p.setSapCode(integrateProductIntegrate.getSapCode());
            p.setSapType(integrateProductIntegrate.getSapType());
            p.setName(integrateProductIntegrate.getName());
            p.setUom(integrateProductIntegrate.getUom());

            product = productRepository.save(p);
        }

        return product;
    }

    private Seller getSeller(ProductIntegrate integrateProductIntegrate) {
        if (integrateProductIntegrate == null ||
            integrateProductIntegrate.getSapCode() == null ||
            integrateProductIntegrate.getSapCode().isEmpty())
            return null;

        Seller seller = sellerRepository.findFirstBySellerID(integrateProductIntegrate.getSellerID());
        if (seller == null) {
            Seller s = new Seller();
            s.setSellerID(integrateProductIntegrate.getSellerID());
            s.setSellerName(integrateProductIntegrate.getSellerName());
            seller = sellerRepository.save(s);

        }

        return seller;
    }


}
