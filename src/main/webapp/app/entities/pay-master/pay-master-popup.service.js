"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var pay_master_model_1 = require("./pay-master.model");
var PayMasterPopupService = (function () {
    function PayMasterPopupService(modalService, router, payMasterService) {
        this.modalService = modalService;
        this.router = router;
        this.payMasterService = payMasterService;
        this.isOpen = false;
    }
    PayMasterPopupService.prototype.open = function (component, id) {
        var _this = this;
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        if (id) {
            this.payMasterService.find(id).subscribe(function (payMaster) {
                _this.payMasterModalRef(component, payMaster);
            });
        }
        else {
            return this.payMasterModalRef(component, new pay_master_model_1.PayMaster());
        }
    };
    PayMasterPopupService.prototype.payMasterModalRef = function (component, payMaster) {
        var _this = this;
        var modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.payMaster = payMaster;
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
    return PayMasterPopupService;
}());
PayMasterPopupService = __decorate([
    core_1.Injectable()
], PayMasterPopupService);
exports.PayMasterPopupService = PayMasterPopupService;
