import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {LoyaltyCardComponent} from './loyalty-card.component';
import {LoyaltyCardDetailComponent} from './loyalty-card-detail.component';
import {LoyaltyCardPopupComponent} from './loyalty-card-dialog.component';
import {LoyaltyCardDeletePopupComponent} from './loyalty-card-delete-dialog.component';

@Injectable()
export class LoyaltyCardResolvePagingParams implements Resolve<any> {

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

export const loyaltyCardRoute: Routes = [
    {
        path: 'loyalty-card',
        component: LoyaltyCardComponent,
        resolve: {
            'pagingParams': LoyaltyCardResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        }
    }, {
        path: 'loyalty-card/:id',
        component: LoyaltyCardDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        }
    }
];

export const loyaltyCardPopupRoute: Routes = [
    {
        path: 'loyalty-card-new',
        component: LoyaltyCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'loyalty-card/:id/edit',
        component: LoyaltyCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'loyalty-card/:id/delete',
        component: LoyaltyCardDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.loyaltyCard.home.title'
        },
        outlet: 'popup'
    }
];
