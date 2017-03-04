"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var pay_master_component_1 = require("./pay-master.component");
var pay_master_detail_component_1 = require("./pay-master-detail.component");
var pay_master_dialog_component_1 = require("./pay-master-dialog.component");
var pay_master_delete_dialog_component_1 = require("./pay-master-delete-dialog.component");
var PayMasterResolvePagingParams = (function () {
    function PayMasterResolvePagingParams(paginationUtil) {
        this.paginationUtil = paginationUtil;
    }
    PayMasterResolvePagingParams.prototype.resolve = function (route, state) {
        var page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        var sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    };
    return PayMasterResolvePagingParams;
}());
PayMasterResolvePagingParams = __decorate([
    core_1.Injectable()
], PayMasterResolvePagingParams);
exports.PayMasterResolvePagingParams = PayMasterResolvePagingParams;
exports.payMasterRoute = [
    {
        path: 'pay-master',
        component: pay_master_component_1.PayMasterComponent,
        resolve: {
            'pagingParams': PayMasterResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        }
    }, {
        path: 'pay-master/:id',
        component: pay_master_detail_component_1.PayMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        }
    }
];
exports.payMasterPopupRoute = [
    {
        path: 'pay-master-new',
        component: pay_master_dialog_component_1.PayMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'pay-master/:id/edit',
        component: pay_master_dialog_component_1.PayMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'pay-master/:id/delete',
        component: pay_master_delete_dialog_component_1.PayMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        },
        outlet: 'popup'
    }
];
