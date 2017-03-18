"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var LoyaltyCardDialogComponent = (function () {
    function LoyaltyCardDialogComponent(activeModal, jhiLanguageService, alertService, loyaltyCardService, eventManager) {
        this.activeModal = activeModal;
        this.jhiLanguageService = jhiLanguageService;
        this.alertService = alertService;
        this.loyaltyCardService = loyaltyCardService;
        this.eventManager = eventManager;
        this.jhiLanguageService.setLocations(['loyaltyCard']);
    }
    LoyaltyCardDialogComponent.prototype.ngOnInit = function () {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    };
    LoyaltyCardDialogComponent.prototype.clear = function () {
        this.activeModal.dismiss('cancel');
    };
    LoyaltyCardDialogComponent.prototype.save = function () {
        var _this = this;
        this.isSaving = true;
        if (this.loyaltyCard.id !== undefined) {
            this.loyaltyCardService.update(this.loyaltyCard)
                .subscribe(function (res) { return _this.onSaveSuccess(res); }, function (res) { return _this.onSaveError(res.json()); });
        }
        else {
            this.loyaltyCardService.create(this.loyaltyCard)
                .subscribe(function (res) { return _this.onSaveSuccess(res); }, function (res) { return _this.onSaveError(res.json()); });
        }
    };
    LoyaltyCardDialogComponent.prototype.onSaveSuccess = function (result) {
        this.eventManager.broadcast({ name: 'loyaltyCardListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    };
    LoyaltyCardDialogComponent.prototype.onSaveError = function (error) {
        this.isSaving = false;
        this.onError(error);
    };
    LoyaltyCardDialogComponent.prototype.onError = function (error) {
        this.alertService.error(error.message, null, null);
    };
    return LoyaltyCardDialogComponent;
}());
LoyaltyCardDialogComponent = __decorate([
    core_1.Component({
        selector: 'jhi-loyalty-card-dialog',
        templateUrl: './loyalty-card-dialog.component.html'
    })
], LoyaltyCardDialogComponent);
exports.LoyaltyCardDialogComponent = LoyaltyCardDialogComponent;
var LoyaltyCardPopupComponent = (function () {
    function LoyaltyCardPopupComponent(route, loyaltyCardPopupService) {
        this.route = route;
        this.loyaltyCardPopupService = loyaltyCardPopupService;
    }
    LoyaltyCardPopupComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.routeSub = this.route.params.subscribe(function (params) {
            if (params['id']) {
                _this.modalRef = _this.loyaltyCardPopupService
                    .open(LoyaltyCardDialogComponent, params['id']);
            }
            else {
                _this.modalRef = _this.loyaltyCardPopupService
                    .open(LoyaltyCardDialogComponent);
            }
        });
    };
    LoyaltyCardPopupComponent.prototype.ngOnDestroy = function () {
        this.routeSub.unsubscribe();
    };
    return LoyaltyCardPopupComponent;
}());
LoyaltyCardPopupComponent = __decorate([
    core_1.Component({
        selector: 'jhi-loyalty-card-popup',
        template: ''
    })
], LoyaltyCardPopupComponent);
exports.LoyaltyCardPopupComponent = LoyaltyCardPopupComponent;
