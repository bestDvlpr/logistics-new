"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var shared_1 = require("../../shared");
var LoyaltyCardComponent = (function () {
    function LoyaltyCardComponent(jhiLanguageService, loyaltyCardService, parseLinks, alertService, principal, activatedRoute, router, eventManager, paginationUtil, paginationConfig) {
        var _this = this;
        this.jhiLanguageService = jhiLanguageService;
        this.loyaltyCardService = loyaltyCardService;
        this.parseLinks = parseLinks;
        this.alertService = alertService;
        this.principal = principal;
        this.activatedRoute = activatedRoute;
        this.router = router;
        this.eventManager = eventManager;
        this.paginationUtil = paginationUtil;
        this.paginationConfig = paginationConfig;
        this.itemsPerPage = shared_1.ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(function (data) {
            _this.page = data['pagingParams'].page;
            _this.previousPage = data['pagingParams'].page;
            _this.reverse = data['pagingParams'].ascending;
            _this.predicate = data['pagingParams'].predicate;
        });
        this.jhiLanguageService.setLocations(['loyaltyCard']);
    }
    LoyaltyCardComponent.prototype.loadAll = function () {
        var _this = this;
        this.loyaltyCardService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(function (res) { return _this.onSuccess(res.json(), res.headers); }, function (res) { return _this.onError(res.json()); });
    };
    LoyaltyCardComponent.prototype.loadPage = function (page) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    };
    LoyaltyCardComponent.prototype.transition = function () {
        this.router.navigate(['/loyalty-card'], { queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    };
    LoyaltyCardComponent.prototype.clear = function () {
        this.page = 0;
        this.router.navigate(['/loyalty-card', {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }]);
        this.loadAll();
    };
    LoyaltyCardComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.loadAll();
        this.principal.identity().then(function (account) {
            _this.currentAccount = account;
        });
        this.registerChangeInLoyaltyCards();
    };
    LoyaltyCardComponent.prototype.ngOnDestroy = function () {
        this.eventManager.destroy(this.eventSubscriber);
    };
    LoyaltyCardComponent.prototype.trackId = function (index, item) {
        return item.id;
    };
    LoyaltyCardComponent.prototype.registerChangeInLoyaltyCards = function () {
        var _this = this;
        this.eventSubscriber = this.eventManager.subscribe('loyaltyCardListModification', function (response) { return _this.loadAll(); });
    };
    LoyaltyCardComponent.prototype.sort = function () {
        var result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    };
    LoyaltyCardComponent.prototype.onSuccess = function (data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.loyaltyCards = data;
    };
    LoyaltyCardComponent.prototype.onError = function (error) {
        this.alertService.error(error.message, null, null);
    };
    return LoyaltyCardComponent;
}());
LoyaltyCardComponent = __decorate([
    core_1.Component({
        selector: 'jhi-loyalty-card',
        templateUrl: './loyalty-card.component.html'
    })
], LoyaltyCardComponent);
exports.LoyaltyCardComponent = LoyaltyCardComponent;
