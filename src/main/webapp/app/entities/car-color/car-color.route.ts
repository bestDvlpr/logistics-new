import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CarColorComponent } from './car-color.component';
import { CarColorDetailComponent } from './car-color-detail.component';
import { CarColorPopupComponent } from './car-color-dialog.component';
import { CarColorDeletePopupComponent } from './car-color-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CarColorResolvePagingParams implements Resolve<any> {

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
