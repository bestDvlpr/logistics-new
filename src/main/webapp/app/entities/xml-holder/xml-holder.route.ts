import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { XmlHolderComponent } from './xml-holder.component';
import { XmlHolderDetailComponent } from './xml-holder-detail.component';
import { XmlHolderPopupComponent } from './xml-holder-dialog.component';
import { XmlHolderDeletePopupComponent } from './xml-holder-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class XmlHolderResolvePagingParams implements Resolve<any> {

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

export const xmlHolderRoute: Routes = [
  {
    path: 'xml-holder',
    component: XmlHolderComponent,
    resolve: {
      'pagingParams': XmlHolderResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.xmlHolder.home.title'
    }
  }, {
    path: 'xml-holder/:id',
    component: XmlHolderDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.xmlHolder.home.title'
    }
  }
];

export const xmlHolderPopupRoute: Routes = [
  {
    path: 'xml-holder-new',
    component: XmlHolderPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.xmlHolder.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'xml-holder/:id/edit',
    component: XmlHolderPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.xmlHolder.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'xml-holder/:id/delete',
    component: XmlHolderDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.xmlHolder.home.title'
    },
    outlet: 'popup'
  }
];
