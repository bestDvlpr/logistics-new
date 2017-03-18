"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var LoyaltyCardDeleteDialogComponent = (function () {
    function LoyaltyCardDeleteDialogComponent(jhiLanguageService, loyaltyCardService, activeModal, eventManager) {
        this.jhiLanguageService = jhiLanguageService;
        this.loyaltyCardService = loyaltyCardService;
        this.activeModal = activeModal;
        this.eventManager = eventManager;
        this.jhiLanguageService.setLocations(['loyaltyCard']);
    }
    LoyaltyCardDeleteDialogComponent.prototype.clear = function () {
        this.activeModal.dismiss('cancel');
    };
    LoyaltyCardDeleteDialogComponent.prototype.confirmDelete = function (id) {
        var _this = this;
        this.loyaltyCardService.delete(id).subscribe(function (response) {
            _this.eventManager.broadcast({
                name: 'loyaltyCardListModification',
                content: 'Deleted an loyaltyCard'
            });
            _this.activeModal.dismiss(true);
        });
    };
    return LoyaltyCardDeleteDialogComponent;
}());
LoyaltyCardDeleteDialogComponent = __decorate([
    core_1.Component({
        selector: 'jhi-loyalty-card-delete-dialog',
        templateUrl: './loyalty-card-delete-dialog.component.html'
    })
], LoyaltyCardDeleteDialogComponent);
exports.LoyaltyCardDeleteDialogComponent = LoyaltyCardDeleteDialogComponent;
var LoyaltyCardDeletePopupComponent = (function () {
    function LoyaltyCardDeletePopupComponent(route, loyaltyCardPopupService) {
        this.route = route;
        this.loyaltyCardPopupService = loyaltyCardPopupService;
    }
    LoyaltyCardDeletePopupComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.routeSub = this.route.params.subscribe(function (params) {
            _this.modalRef = _this.loyaltyCardPopupService
                .open(LoyaltyCardDeleteDialogComponent, params['id']);
        });
    };
    LoyaltyCardDeletePopupComponent.prototype.ngOnDestroy = function () {
        this.routeSub.unsubscribe();
    };
    return LoyaltyCardDeletePopupComponent;
}());
LoyaltyCardDeletePopupComponent = __decorate([
    core_1.Component({
        selector: 'jhi-loyalty-card-delete-popup',
        template: ''
    })
], LoyaltyCardDeletePopupComponent);
exports.LoyaltyCardDeletePopupComponent = LoyaltyCardDeletePopupComponent;
