"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var loyalty_card_model_1 = require("./loyalty-card.model");
var LoyaltyCardPopupService = (function () {
    function LoyaltyCardPopupService(modalService, router, loyaltyCardService) {
        this.modalService = modalService;
        this.router = router;
        this.loyaltyCardService = loyaltyCardService;
        this.isOpen = false;
    }
    LoyaltyCardPopupService.prototype.open = function (component, id) {
        var _this = this;
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        if (id) {
            this.loyaltyCardService.find(id).subscribe(function (loyaltyCard) {
                _this.loyaltyCardModalRef(component, loyaltyCard);
            });
        }
        else {
            return this.loyaltyCardModalRef(component, new loyalty_card_model_1.LoyaltyCard());
        }
    };
    LoyaltyCardPopupService.prototype.loyaltyCardModalRef = function (component, loyaltyCard) {
        var _this = this;
        var modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.loyaltyCard = loyaltyCard;
        modalRef.result.then(function (result) {
            console.log("Closed with: " + result);
            _this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
            _this.isOpen = false;
        }, function (reason) {
            console.log("Dismissed " + reason);
            _this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
            _this.isOpen = false;
        });
        return modalRef;
    };
    return LoyaltyCardPopupService;
}());
LoyaltyCardPopupService = __decorate([
    core_1.Injectable()
], LoyaltyCardPopupService);
exports.LoyaltyCardPopupService = LoyaltyCardPopupService;
