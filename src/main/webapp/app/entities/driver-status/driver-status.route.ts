import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DriverStatusComponent } from './driver-status.component';
import { DriverStatusDetailComponent } from './driver-status-detail.component';
import { DriverStatusPopupComponent } from './driver-status-dialog.component';
import { DriverStatusDeletePopupComponent } from './driver-status-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DriverStatusResolvePagingParams implements Resolve<any> {

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

export const driverStatusRoute: Routes = [
  {
    path: 'driver-status',
    component: DriverStatusComponent,
    resolve: {
      'pagingParams': DriverStatusResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.driverStatus.home.title'
    }
  }, {
    path: 'driver-status/:id',
    component: DriverStatusDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.driverStatus.home.title'
    }
  }
];

export const driverStatusPopupRoute: Routes = [
  {
    path: 'driver-status-new',
    component: DriverStatusPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.driverStatus.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'driver-status/:id/edit',
    component: DriverStatusPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.driverStatus.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'driver-status/:id/delete',
    component: DriverStatusDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.driverStatus.home.title'
    },
    outlet: 'popup'
  }
];
