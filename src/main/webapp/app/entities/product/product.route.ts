import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {ProductComponent} from './product.component';
import {ProductDetailComponent} from './product-detail.component';
import {ProductPopupComponent} from './product-dialog.component';
import {ProductDeletePopupComponent} from './product-delete-dialog.component';

@Injectable()
export class ProductResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const productRoute: Routes = [
    {
        path: 'product',
        component: ProductComponent,
        resolve: {
            'pagingParams': ProductResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.product.home.title'
        }
    }, {
        path: 'product/:id',
        component: ProductDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.product.home.title'
        }
    }
];

export const productPopupRoute: Routes = [
    {
        path: 'product-new',
        component: ProductPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.product.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'product/:id/edit',
        component: ProductPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.product.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'product/:id/delete',
        component: ProductDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.product.home.title'
        },
        outlet: 'popup'
    }
];
