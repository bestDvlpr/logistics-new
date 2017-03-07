package uz.hasan.service.impl;

import uz.hasan.domain.*;
import uz.hasan.domain.enumeration.*;
import uz.hasan.domain.enumeration.PaymentType;
import uz.hasan.repository.*;
import uz.hasan.service.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.service.dto.IntegrateDTO;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.util.List;

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
        receipt.setStatus(ReceiptStatus.NEW);

        receipt = receiptRepository.save(receipt);

        updateOrCreateProductEntries(receipt, integrateDTO.getProducts());

        updateOrCreatePayTypes(receipt, integrateDTO.getPayments());


    }

    private void updateOrCreateProductEntries(Receipt savedReceipt, List<IntegrateDTO.Product> products) throws ValidationException {
        List<ProductEntry> productEntries = productEntryRepository.findByReceiptId(savedReceipt.getId());
        if (productEntries != null && !productEntries.isEmpty()) {
            log.debug("Products found: " + String.valueOf(productEntries.size()));

            for (IntegrateDTO.Product product : products) {
                if (SalesType.DELIVERY == SalesType.get(product.getDeliveryFlag())) {

                    for (int qty = 0; qty < product.getQty().intValue(); qty++) {


                        boolean hasEntry = false;

                        for (ProductEntry productEntry : productEntries) {

                            if (product.eq(productEntry)) {
                                if (savedReceipt.getDocType() == DocType.RETURN)
                                    productEntry.setCancelled(true);
                                else
                                    productEntry.setCancelled(false);

                                productEntryRepository.save(productEntry);
                                hasEntry = true;

                            }
                        }

                        if (!hasEntry) {

                            SalesType salesType = SalesType.get(product.getDeliveryFlag());

                            SalesPlace salesPlace = SalesPlace.get(product.getHallFlag());

                            DefectFlag defectFlag = DefectFlag.get(product.getDefectFlag());

                            VirtualFlag virtualFlag = VirtualFlag.get(product.getVirtualFlag());

                            Product p = getProduct(product);

                            Seller seller = getSeller(product);


                            ProductEntry productEntry = new ProductEntry();
                            productEntry.setQty(product.getQty());
                            productEntry.setComment(product.getComment());
                            productEntry.setDiscount(product.getDiscount());
                            productEntry.setPrice(product.getPrice());
                            productEntry.setReason(product.getReason());
                            productEntry.setGuid(product.getGuid());

                            productEntry.setDeliveryFlag(salesType);
                            productEntry.setHallFlag(salesPlace);
                            productEntry.setDefectFlag(defectFlag);
                            productEntry.setVirtualFlag(virtualFlag);
                            productEntry.setProduct(p);
                            productEntry.setSellerID(seller);
                            productEntry.setReceipt(savedReceipt);

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

    }


    private void updateOrCreatePayTypes(Receipt savedReceipt, List<IntegrateDTO.Payment> payments) {
        List<PayType> payTypes = payTypeRepository.findByReceiptId(savedReceipt.getId());

        if (payTypes != null && !payTypes.isEmpty()) {
            log.debug("Payments found: " + String.valueOf(payTypes.size()));

            for (PayType payType : payTypes) {
                payTypeRepository.delete(payType);
            }
        }

        for (IntegrateDTO.Payment payment : payments) {

            try {
                PaymentType paymentType = PaymentType.get(payment.getType());

                PayType payType = new PayType();
                payType.setReceipt(savedReceipt);
                payType.setAmount(payment.getAmount());
                payType.setSerial(payment.getSerial());
                payType.setSapCode(payment.getSapCode());

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

    private Product getProduct(IntegrateDTO.Product integrateProduct) {
        if (integrateProduct == null ||
            integrateProduct.getSapCode() == null ||
            integrateProduct.getSapCode().isEmpty())
            return null;

        Product product = productRepository.findFirstBySapCode(integrateProduct.getSapCode());
        if (product == null) {
            Product p = new Product();
            p.setSapCode(integrateProduct.getSapCode());
            p.setSapType(integrateProduct.getSapType());
            p.setName(integrateProduct.getName());
            p.setUom(integrateProduct.getUom());

            product = productRepository.save(p);
        }

        return product;
    }

    private Seller getSeller(IntegrateDTO.Product integrateProduct) {
        if (integrateProduct == null ||
            integrateProduct.getSapCode() == null ||
            integrateProduct.getSapCode().isEmpty())
            return null;

        Seller seller = sellerRepository.findFirstBySellerID(integrateProduct.getSellerID());
        if (seller == null) {
            Seller s = new Seller();
            s.setSellerID(integrateProduct.getSellerID());
            s.setSellerName(integrateProduct.getSellerName());
            seller = sellerRepository.save(s);

        }

        return seller;
    }


}
