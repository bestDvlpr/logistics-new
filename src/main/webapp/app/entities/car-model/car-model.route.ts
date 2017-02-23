import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CarModelComponent } from './car-model.component';
import { CarModelDetailComponent } from './car-model-detail.component';
import { CarModelPopupComponent } from './car-model-dialog.component';
import { CarModelDeletePopupComponent } from './car-model-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CarModelResolvePagingParams implements Resolve<any> {

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