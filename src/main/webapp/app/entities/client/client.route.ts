import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {Principal} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {ClientComponent} from './client.component';
import {ClientDetailComponent} from './client-detail.component';
import {ClientPopupComponent} from './client-dialog.component';
import {ClientDeletePopupComponent} from './client-delete-dialog.component';

@Injectable()
export class ClientResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil, private principal: Principal) {
    }

    canActivate() {
        return this.principal.identity().then((account) => this.principal.hasAnyAuthority(['ROLE_USER']));
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

export const clientRoute: Routes = [
    {
        path: 'client',
        component: ClientComponent,
        resolve: {
            'pagingParams': ClientResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.client.home.title'
        }
    }, {
        path: 'client/:id',
        component: ClientDetailComponent,
        resolve: {
            'pagingParams': ClientResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.client.home.title'
        }
    }
];

export const clientPopupRoute: Routes = [
    {
        path: 'client-new',
        component: ClientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.client.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'client/:id/edit',
        component: ClientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.client.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'client/:id/delete',
        component: ClientDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.client.home.title'
        },
        outlet: 'popup'
    }
];
