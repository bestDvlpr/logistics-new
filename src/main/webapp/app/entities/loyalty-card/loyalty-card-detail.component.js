"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var LoyaltyCardDetailComponent = (function () {
    function LoyaltyCardDetailComponent(jhiLanguageService, loyaltyCardService, route) {
        this.jhiLanguageService = jhiLanguageService;
        this.loyaltyCardService = loyaltyCardService;
        this.route = route;
        this.jhiLanguageService.setLocations(['loyaltyCard']);
    }
    LoyaltyCardDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.subscription = this.route.params.subscribe(function (params) {
            _this.load(params['id']);
        });
    };
    LoyaltyCardDetailComponent.prototype.load = function (id) {
        var _this = this;
        this.loyaltyCardService.find(id).subscribe(function (loyaltyCard) {
            _this.loyaltyCard = loyaltyCard;
        });
    };
    LoyaltyCardDetailComponent.prototype.previousState = function () {
        window.history.back();
    };
    LoyaltyCardDetailComponent.prototype.ngOnDestroy = function () {
        this.subscription.unsubscribe();
    };
    return LoyaltyCardDetailComponent;
}());
LoyaltyCardDetailComponent = __decorate([
    core_1.Component({
        selector: 'jhi-loyalty-card-detail',
        templateUrl: './loyalty-card-detail.component.html'
    })
], LoyaltyCardDetailComponent);
exports.LoyaltyCardDetailComponent = LoyaltyCardDetailComponent;
