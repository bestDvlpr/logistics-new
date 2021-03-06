import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {AddressComponent} from './address.component';
import {AddressDetailComponent} from './address-detail.component';
import {AddressPopupComponent} from './address-dialog.component';
import {AddressDeletePopupComponent} from './address-delete-dialog.component';

@Injectable()
export class AddressResolvePagingParams implements Resolve<any> {

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

export const addressRoute: Routes = [
    {
        path: 'address',
        component: AddressComponent,
        resolve: {
            'pagingParams': AddressResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.address.home.title'
        }
    }, {
        path: 'address/:id',
        component: AddressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.address.home.title'
        }
    }
];

export const addressPopupRoute: Routes = [
    {
        path: 'address-new',
        component: AddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.address.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'address/:id/edit',
        component: AddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.address.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'address/:id/delete',
        component: AddressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.address.home.title'
        },
        outlet: 'popup'
    }
];
