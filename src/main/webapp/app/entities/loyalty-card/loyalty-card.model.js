"use strict";
var LoyaltyCard = (function () {
    function LoyaltyCard(id, loyaltyCardID, loyaltyCardBonus, loyaltyCardAmount) {
        this.id = id;
        this.loyaltyCardID = loyaltyCardID;
        this.loyaltyCardBonus = loyaltyCardBonus;
        this.loyaltyCardAmount = loyaltyCardAmount;
    }
    return LoyaltyCard;
}());
exports.LoyaltyCard = LoyaltyCard;
