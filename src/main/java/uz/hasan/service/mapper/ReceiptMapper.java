package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.ReceiptDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Receipt and its DTO ReceiptDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserMapper.class, })
public interface ReceiptMapper {

    @Mapping(source = "payMaster.id", target = "payMasterId")
    @Mapping(source = "payMaster.payMasterName", target = "payMasterPayMasterName")
    @Mapping(source = "loyaltyCard.id", target = "loyaltyCardId")
    @Mapping(source = "loyaltyCard.loyaltyCardID", target = "loyaltyCardLoyaltyCardID")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.firstName", target = "clientFirstName")
    @Mapping(source = "sentBy.id", target = "sentById")
    @Mapping(source = "sentBy.login", target = "sentByLogin")
    @Mapping(source = "markedAsDeliveredBy.id", target = "markedAsDeliveredById")
    @Mapping(source = "markedAsDeliveredBy.login", target = "markedAsDeliveredByLogin")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetAddress", target = "addressStreetAddress")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "receiver.id", target = "receiver.id")
    @Mapping(source = "receiver.name", target = "receiver.name")
    @Mapping(source = "receiver.type", target = "receiver.type")
    ReceiptDTO receiptToReceiptDTO(Receipt receipt);

    List<ReceiptDTO> receiptsToReceiptDTOs(List<Receipt> receipts);

    @Mapping(source = "payMasterId", target = "payMaster")
    @Mapping(source = "loyaltyCardId", target = "loyaltyCard")
    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "productEntries", ignore = true)
    @Mapping(target = "payTypes", ignore = true)
    @Mapping(source = "sentById", target = "sentBy")
    @Mapping(source = "markedAsDeliveredById", target = "markedAsDeliveredBy")
    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "receiverId", target = "receiver")
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

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }

    default Company companyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
