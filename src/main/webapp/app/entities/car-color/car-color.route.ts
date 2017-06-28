import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {CarColorComponent} from './car-color.component';
import {CarColorDetailComponent} from './car-color-detail.component';
import {CarColorPopupComponent} from './car-color-dialog.component';
import {CarColorDeletePopupComponent} from './car-color-delete-dialog.component';

@Injectable()
export class CarColorResolvePagingParams implements Resolve<any> {

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

export const carColorRoute: Routes = [
    {
        path: 'car-color',
        component: CarColorComponent,
        resolve: {
            'pagingParams': CarColorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carColor.home.title'
        }
    }, {
        path: 'car-color/:id',
        component: CarColorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carColor.home.title'
        }
    }
];

export const carColorPopupRoute: Routes = [
    {
        path: 'car-color-new',
        component: CarColorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carColor.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'car-color/:id/edit',
        component: CarColorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carColor.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'car-color/:id/delete',
        component: CarColorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.carColor.home.title'
        },
        outlet: 'popup'
    }
];
