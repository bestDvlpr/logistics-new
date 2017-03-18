"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var PayMasterDeleteDialogComponent = (function () {
    function PayMasterDeleteDialogComponent(jhiLanguageService, payMasterService, activeModal, eventManager) {
        this.jhiLanguageService = jhiLanguageService;
        this.payMasterService = payMasterService;
        this.activeModal = activeModal;
        this.eventManager = eventManager;
        this.jhiLanguageService.setLocations(['payMaster']);
    }
    PayMasterDeleteDialogComponent.prototype.clear = function () {
        this.activeModal.dismiss('cancel');
    };
    PayMasterDeleteDialogComponent.prototype.confirmDelete = function (id) {
        var _this = this;
        this.payMasterService.delete(id).subscribe(function (response) {
            _this.eventManager.broadcast({
                name: 'payMasterListModification',
                content: 'Deleted an payMaster'
            });
            _this.activeModal.dismiss(true);
        });
    };
    return PayMasterDeleteDialogComponent;
}());
PayMasterDeleteDialogComponent = __decorate([
    core_1.Component({
        selector: 'jhi-pay-master-delete-dialog',
        templateUrl: './pay-master-delete-dialog.component.html'
    })
], PayMasterDeleteDialogComponent);
exports.PayMasterDeleteDialogComponent = PayMasterDeleteDialogComponent;
var PayMasterDeletePopupComponent = (function () {
    function PayMasterDeletePopupComponent(route, payMasterPopupService) {
        this.route = route;
        this.payMasterPopupService = payMasterPopupService;
    }
    PayMasterDeletePopupComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.routeSub = this.route.params.subscribe(function (params) {
            _this.modalRef = _this.payMasterPopupService
                .open(PayMasterDeleteDialogComponent, params['id']);
        });
    };
    PayMasterDeletePopupComponent.prototype.ngOnDestroy = function () {
        this.routeSub.unsubscribe();
    };
    return PayMasterDeletePopupComponent;
}());
PayMasterDeletePopupComponent = __decorate([
    core_1.Component({
        selector: 'jhi-pay-master-delete-popup',
        template: ''
    })
], PayMasterDeletePopupComponent);
exports.PayMasterDeletePopupComponent = PayMasterDeletePopupComponent;
