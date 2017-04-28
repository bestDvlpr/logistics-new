import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CompanyComponent } from './company.component';
import { CompanyDetailComponent } from './company-detail.component';
import { CompanyPopupComponent } from './company-dialog.component';
import { CompanyDeletePopupComponent } from './company-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CompanyResolvePagingParams implements Resolve<any> {

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

export const companyRoute: Routes = [
  {
    path: 'company',
    component: CompanyComponent,
    resolve: {
      'pagingParams': CompanyResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.company.home.title'
    }
  }, {
    path: 'company/:id',
    component: CompanyDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.company.home.title'
    }
  }
];

export const companyPopupRoute: Routes = [
  {
    path: 'company-new',
    component: CompanyPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.company.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'company/:id/edit',
    component: CompanyPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.company.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'company/:id/delete',
    component: CompanyDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.company.home.title'
    },
    outlet: 'popup'
  }
];
