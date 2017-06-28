import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {CarModelComponent} from './car-model.component';
import {CarModelDetailComponent} from './car-model-detail.component';
import {CarModelPopupComponent} from './car-model-dialog.component';
import {CarModelDeletePopupComponent} from './car-model-delete-dialog.component';

@Injectable()
export class CarModelResolvePagingParams implements Resolve<any> {

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

export const carModelRoute: Routes = [
    {
        path: 'car-model',
        component: CarModelComponent,
        resolve: {
            'pagingParams': CarModelResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carModel.home.title'
        }
    }, {
        path: 'car-model/:id',
        component: CarModelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carModel.home.title'
        }
    }
];

export const carModelPopupRoute: Routes = [
    {
        path: 'car-model-new',
        component: CarModelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carModel.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'car-model/:id/edit',
        component: CarModelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carModel.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'car-model/:id/delete',
        component: CarModelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carModel.home.title'
        },
        outlet: 'popup'
    }
];
