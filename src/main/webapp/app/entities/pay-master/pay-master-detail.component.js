"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var PayMasterDetailComponent = (function () {
    function PayMasterDetailComponent(jhiLanguageService, payMasterService, route) {
        this.jhiLanguageService = jhiLanguageService;
        this.payMasterService = payMasterService;
        this.route = route;
        this.jhiLanguageService.setLocations(['payMaster']);
    }
    PayMasterDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.subscription = this.route.params.subscribe(function (params) {
            _this.load(params['id']);
        });
    };
    PayMasterDetailComponent.prototype.load = function (id) {
        var _this = this;
        this.payMasterService.find(id).subscribe(function (payMaster) {
            _this.payMaster = payMaster;
        });
    };
    PayMasterDetailComponent.prototype.previousState = function () {
        window.history.back();
    };
    PayMasterDetailComponent.prototype.ngOnDestroy = function () {
        this.subscription.unsubscribe();
    };
    return PayMasterDetailComponent;
}());
PayMasterDetailComponent = __decorate([
    core_1.Component({
        selector: 'jhi-pay-master-detail',
        templateUrl: './pay-master-detail.component.html'
    })
], PayMasterDetailComponent);
exports.PayMasterDetailComponent = PayMasterDetailComponent;
