import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {PayMasterComponent} from './pay-master.component';
import {PayMasterDetailComponent} from './pay-master-detail.component';
import {PayMasterPopupComponent} from './pay-master-dialog.component';
import {PayMasterDeletePopupComponent} from './pay-master-delete-dialog.component';

@Injectable()
export class PayMasterResolvePagingParams implements Resolve<any> {

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

export const payMasterRoute: Routes = [
    {
        path: 'pay-master',
        component: PayMasterComponent,
        resolve: {
            'pagingParams': PayMasterResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        }
    }, {
        path: 'pay-master/:id',
        component: PayMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        }
    }
];

export const payMasterPopupRoute: Routes = [
    {
        path: 'pay-master-new',
        component: PayMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'pay-master/:id/edit',
        component: PayMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'pay-master/:id/delete',
        component: PayMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.payMaster.home.title'
        },
        outlet: 'popup'
    }
];
