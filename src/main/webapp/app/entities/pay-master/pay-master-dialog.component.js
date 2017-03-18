"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var PayMasterDialogComponent = (function () {
    function PayMasterDialogComponent(activeModal, jhiLanguageService, alertService, payMasterService, eventManager) {
        this.activeModal = activeModal;
        this.jhiLanguageService = jhiLanguageService;
        this.alertService = alertService;
        this.payMasterService = payMasterService;
        this.eventManager = eventManager;
        this.jhiLanguageService.setLocations(['payMaster']);
    }
    PayMasterDialogComponent.prototype.ngOnInit = function () {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    };
    PayMasterDialogComponent.prototype.clear = function () {
        this.activeModal.dismiss('cancel');
    };
    PayMasterDialogComponent.prototype.save = function () {
        var _this = this;
        this.isSaving = true;
        if (this.payMaster.id !== undefined) {
            this.payMasterService.update(this.payMaster)
                .subscribe(function (res) { return _this.onSaveSuccess(res); }, function (res) { return _this.onSaveError(res.json()); });
        }
        else {
            this.payMasterService.create(this.payMaster)
                .subscribe(function (res) { return _this.onSaveSuccess(res); }, function (res) { return _this.onSaveError(res.json()); });
        }
    };
    PayMasterDialogComponent.prototype.onSaveSuccess = function (result) {
        this.eventManager.broadcast({ name: 'payMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    };
    PayMasterDialogComponent.prototype.onSaveError = function (error) {
        this.isSaving = false;
        this.onError(error);
    };
    PayMasterDialogComponent.prototype.onError = function (error) {
        this.alertService.error(error.message, null, null);
    };
    return PayMasterDialogComponent;
}());
PayMasterDialogComponent = __decorate([
    core_1.Component({
        selector: 'jhi-pay-master-dialog',
        templateUrl: './pay-master-dialog.component.html'
    })
], PayMasterDialogComponent);
exports.PayMasterDialogComponent = PayMasterDialogComponent;
var PayMasterPopupComponent = (function () {
    function PayMasterPopupComponent(route, payMasterPopupService) {
        this.route = route;
        this.payMasterPopupService = payMasterPopupService;
    }
    PayMasterPopupComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.routeSub = this.route.params.subscribe(function (params) {
            if (params['id']) {
                _this.modalRef = _this.payMasterPopupService
                    .open(PayMasterDialogComponent, params['id']);
            }
            else {
                _this.modalRef = _this.payMasterPopupService
                    .open(PayMasterDialogComponent);
            }
        });
    };
    PayMasterPopupComponent.prototype.ngOnDestroy = function () {
        this.routeSub.unsubscribe();
    };
    return PayMasterPopupComponent;
}());
PayMasterPopupComponent = __decorate([
    core_1.Component({
        selector: 'jhi-pay-master-popup',
        template: ''
    })
], PayMasterPopupComponent);
exports.PayMasterPopupComponent = PayMasterPopupComponent;
