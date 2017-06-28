import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {XmlHolderComponent} from './xml-holder.component';
import {XmlHolderDetailComponent} from './xml-holder-detail.component';
import {XmlHolderPopupComponent} from './xml-holder-dialog.component';
import {XmlHolderDeletePopupComponent} from './xml-holder-delete-dialog.component';

@Injectable()
export class XmlHolderResolvePagingParams implements Resolve<any> {

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
