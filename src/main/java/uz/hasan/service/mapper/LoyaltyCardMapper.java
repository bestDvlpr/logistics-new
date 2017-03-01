package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.LoyaltyCardDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LoyaltyCard and its DTO LoyaltyCardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoyaltyCardMapper {

    LoyaltyCardDTO loyaltyCardToLoyaltyCardDTO(LoyaltyCard loyaltyCard);

    List<LoyaltyCardDTO> loyaltyCardsToLoyaltyCardDTOs(List<LoyaltyCard> loyaltyCards);

    LoyaltyCard loyaltyCardDTOToLoyaltyCard(LoyaltyCardDTO loyaltyCardDTO);

    List<LoyaltyCard> loyaltyCardDTOsToLoyaltyCards(List<LoyaltyCardDTO> loyaltyCardDTOs);
}
