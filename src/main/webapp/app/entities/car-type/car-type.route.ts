import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CarTypeComponent } from './car-type.component';
import { CarTypeDetailComponent } from './car-type-detail.component';
import { CarTypePopupComponent } from './car-type-dialog.component';
import { CarTypeDeletePopupComponent } from './car-type-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CarTypeResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

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

export const carTypeRoute: Routes = [
  {
    path: 'car-type',
    component: CarTypeComponent,
    resolve: {
      'pagingParams': CarTypeResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.carType.home.title'
    }
  }, {
    path: 'car-type/:id',
    component: CarTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.carType.home.title'
    }
  }
];

export const carTypePopupRoute: Routes = [
  {
    path: 'car-type-new',
    component: CarTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.carType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'car-type/:id/edit',
    component: CarTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.carType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'car-type/:id/delete',
    component: CarTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.carType.home.title'
    },
    outlet: 'popup'
  }
];
