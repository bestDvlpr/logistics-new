"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var shared_1 = require("../../shared");
var PayMasterComponent = (function () {
    function PayMasterComponent(jhiLanguageService, payMasterService, parseLinks, alertService, principal, activatedRoute, router, eventManager, paginationUtil, paginationConfig) {
        var _this = this;
        this.jhiLanguageService = jhiLanguageService;
        this.payMasterService = payMasterService;
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
        this.jhiLanguageService.setLocations(['payMaster']);
    }
    PayMasterComponent.prototype.loadAll = function () {
        var _this = this;
        this.payMasterService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(function (res) { return _this.onSuccess(res.json(), res.headers); }, function (res) { return _this.onError(res.json()); });
    };
    PayMasterComponent.prototype.loadPage = function (page) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    };
    PayMasterComponent.prototype.transition = function () {
        this.router.navigate(['/pay-master'], { queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    };
    PayMasterComponent.prototype.clear = function () {
        this.page = 0;
        this.router.navigate(['/pay-master', {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }]);
        this.loadAll();
    };
    PayMasterComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.loadAll();
        this.principal.identity().then(function (account) {
            _this.currentAccount = account;
        });
        this.registerChangeInPayMasters();
    };
    PayMasterComponent.prototype.ngOnDestroy = function () {
        this.eventManager.destroy(this.eventSubscriber);
    };
    PayMasterComponent.prototype.trackId = function (index, item) {
        return item.id;
    };
    PayMasterComponent.prototype.registerChangeInPayMasters = function () {
        var _this = this;
        this.eventSubscriber = this.eventManager.subscribe('payMasterListModification', function (response) { return _this.loadAll(); });
    };
    PayMasterComponent.prototype.sort = function () {
        var result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    };
    PayMasterComponent.prototype.onSuccess = function (data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.payMasters = data;
    };
    PayMasterComponent.prototype.onError = function (error) {
        this.alertService.error(error.message, null, null);
    };
    return PayMasterComponent;
}());
PayMasterComponent = __decorate([
    core_1.Component({
        selector: 'jhi-pay-master',
        templateUrl: './pay-master.component.html'
    })
], PayMasterComponent);
exports.PayMasterComponent = PayMasterComponent;