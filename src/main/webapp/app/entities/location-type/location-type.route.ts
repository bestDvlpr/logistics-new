import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LocationTypeComponent } from './location-type.component';
import { LocationTypeDetailComponent } from './location-type-detail.component';
import { LocationTypePopupComponent } from './location-type-dialog.component';
import { LocationTypeDeletePopupComponent } from './location-type-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class LocationTypeResolvePagingParams implements Resolve<any> {

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

export const locationTypeRoute: Routes = [
  {
    path: 'location-type',
    component: LocationTypeComponent,
    resolve: {
      'pagingParams': LocationTypeResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.locationType.home.title'
    }
  }, {
    path: 'location-type/:id',
    component: LocationTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.locationType.home.title'
    }
  }
];

export const locationTypePopupRoute: Routes = [
  {
    path: 'location-type-new',
    component: LocationTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.locationType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'location-type/:id/edit',
    component: LocationTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.locationType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'location-type/:id/delete',
    component: LocationTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.locationType.home.title'
    },
    outlet: 'popup'
  }
];
