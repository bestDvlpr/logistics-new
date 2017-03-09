package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.ReceiptDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Receipt and its DTO ReceiptDTO.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, AddressMapper.class, })
public interface ReceiptMapper {

    @Mapping(source = "payMaster.id", target = "payMasterId")
    @Mapping(source = "payMaster.payMasterName", target = "payMasterPayMasterName")
    @Mapping(source = "loyaltyCard.id", target = "loyaltyCardId")
    @Mapping(source = "loyaltyCard.loyaltyCardID", target = "loyaltyCardLoyaltyCardID")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.firstName", target = "clientFirstName")
    ReceiptDTO receiptToReceiptDTO(Receipt receipt);

    List<ReceiptDTO> receiptsToReceiptDTOs(List<Receipt> receipts);

    @Mapping(source = "payMasterId", target = "payMaster")
    @Mapping(source = "loyaltyCardId", target = "loyaltyCard")
    @Mapping(source = "clientId", target = "client.id")
    @Mapping(target = "productEntries", ignore = true)
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

    default Client clientFromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }

    default Car carFromId(Long id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
