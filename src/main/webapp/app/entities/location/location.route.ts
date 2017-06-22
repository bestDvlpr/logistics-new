import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from "@angular/router";
import {JhiPaginationUtil} from "ng-jhipster";

import {LocationComponent} from "./location.component";
import {LocationDetailComponent} from "./location-detail.component";
import {LocationPopupComponent} from "./location-dialog.component";
import {LocationDeletePopupComponent} from "./location-delete-dialog.component";

@Injectable()
export class LocationResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {
    }

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

export const locationRoute: Routes = [
    {
        path: 'location',
        component: LocationComponent,
        resolve: {
            'pagingParams': LocationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.location.home.title'
        }
    }, {
        path: 'location/:id',
        component: LocationDetailComponent,
        resolve: {
            'pagingParams': LocationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.location.home.title'
        }
    }
];

export const locationPopupRoute: Routes = [
    {
        path: 'location-new',
        component: LocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.location.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'location/:id/edit',
        component: LocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.location.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'location/:id/delete',
        component: LocationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.location.home.title'
        },
        outlet: 'popup'
    }
];
