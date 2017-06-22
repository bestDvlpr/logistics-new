import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from "@angular/router";

import {JhiPaginationUtil} from "ng-jhipster";

import {ProductEntryComponent} from "./product-entry.component";
import {ProductEntryDetailComponent} from "./product-entry-detail.component";
import {ProductEntryPopupComponent} from "./product-entry-dialog.component";
import {ProductEntryDeletePopupComponent} from "./product-entry-delete-dialog.component";

import {ProductEntryDeliveryComponent} from "./product-entry-delivery.component";
import {ProductEntryDoneComponent} from "./product-entry-done.component";

@Injectable()
export class ProductEntryResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const productEntryRoute: Routes = [
    {
        path: 'product-entry',
        component: ProductEntryComponent,
        resolve: {
            'pagingParams': ProductEntryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.productEntry.home.title'
        }
    }, {
        path: 'product-entry-delivery',
        component: ProductEntryDeliveryComponent,
        data: {
            authorities: ['ROLE_CASHIER'],
            pageTitle: 'logisticsApp.productEntry.home.title'
        }
    }, {
        path: 'product-entry-done',
        component: ProductEntryDoneComponent,
        data: {
            authorities: ['ROLE_DISPATCHER'],
            pageTitle: 'logisticsApp.productEntry.home.title'
        }
    }, {
        path: 'product-entry/:id',
        component: ProductEntryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.productEntry.home.title'
        }
    }
];

export const productEntryPopupRoute: Routes = [
    {
        path: 'product-entry-new',
        component: ProductEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.productEntry.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'product-entry/:id/edit',
        component: ProductEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.productEntry.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'product-entry/:id/delete',
        component: ProductEntryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.productEntry.home.title'
        },
        outlet: 'popup'
    }
];
