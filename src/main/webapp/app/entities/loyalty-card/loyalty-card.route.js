"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var loyalty_card_component_1 = require("./loyalty-card.component");
var loyalty_card_detail_component_1 = require("./loyalty-card-detail.component");
var loyalty_card_dialog_component_1 = require("./loyalty-card-dialog.component");
var loyalty_card_delete_dialog_component_1 = require("./loyalty-card-delete-dialog.component");
var LoyaltyCardResolvePagingParams = (function () {
    function LoyaltyCardResolvePagingParams(paginationUtil) {
        this.paginationUtil = paginationUtil;
    }
    LoyaltyCardResolvePagingParams.prototype.resolve = function (route, state) {
        var page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        var sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    };
    return LoyaltyCardResolvePagingParams;
}());
LoyaltyCardResolvePagingParams = __decorate([
    core_1.Injectable()
], LoyaltyCardResolvePagingParams);
exports.LoyaltyCardResolvePagingParams = LoyaltyCardResolvePagingParams;
exports.loyaltyCardRoute = [
    {
        path: 'loyalty-card',
        component: loyalty_card_component_1.LoyaltyCardComponent,
        resolve: {
            'pagingParams': LoyaltyCardResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        }
    }, {
        path: 'loyalty-card/:id',
        component: loyalty_card_detail_component_1.LoyaltyCardDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        }
    }
];
exports.loyaltyCardPopupRoute = [
    {
        path: 'loyalty-card-new',
        component: loyalty_card_dialog_component_1.LoyaltyCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'loyalty-card/:id/edit',
        component: loyalty_card_dialog_component_1.LoyaltyCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'loyalty-card/:id/delete',
        component: loyalty_card_delete_dialog_component_1.LoyaltyCardDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        },
        outlet: 'popup'
    }
];
