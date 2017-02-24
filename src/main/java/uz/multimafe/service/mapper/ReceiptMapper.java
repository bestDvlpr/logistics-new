package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.ReceiptDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Receipt and its DTO ReceiptDTO.
 */
@Mapper(componentModel = "spring", uses = {DriverMapper.class, })
public interface ReceiptMapper {

    @Mapping(source = "payMaster.id", target = "payMasterId")
    @Mapping(source = "payMaster.payMasterName", target = "payMasterPayMasterName")
    @Mapping(source = "loyaltyCard.id", target = "loyaltyCardId")
    @Mapping(source = "loyaltyCard.loyaltyCardID", target = "loyaltyCardLoyaltyCardID")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "payType.id", target = "payTypeId")
    @Mapping(source = "payType.sapCode", target = "payTypeSapCode")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    ReceiptDTO receiptToReceiptDTO(Receipt receipt);

    List<ReceiptDTO> receiptsToReceiptDTOs(List<Receipt> receipts);

    @Mapping(source = "payMasterId", target = "payMaster")
    @Mapping(source = "loyaltyCardId", target = "loyaltyCard")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "payTypeId", target = "payType")
    @Mapping(source = "statusId", target = "status")
    Receipt receiptDTOToReceipt(ReceiptDTO receiptDTO);

    List<Receipt> receiptDTOsToReceipts(List<ReceiptDTO> receiptDTOs);

    default PayMaster payMasterFromId(Long id) {
        if (id == null) {
            return null;
        }
        PayMaster payMaster = new PayMaster();
        payMaster.setId(id);
        return payMaster;
    }

    default LoyaltyCard loyaltyCardFromId(Long id) {
        if (id == null) {
            return null;
        }
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        loyaltyCard.setId(id);
        return loyaltyCard;
    }

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

    default PayType payTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PayType payType = new PayType();
        payType.setId(id);
        return payType;
    }

    default ReceiptStatus receiptStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        ReceiptStatus receiptStatus = new ReceiptStatus();
        receiptStatus.setId(id);
        return receiptStatus;
    }

    default Driver driverFromId(Long id) {
        if (id == null) {
            return null;
        }
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }
}
