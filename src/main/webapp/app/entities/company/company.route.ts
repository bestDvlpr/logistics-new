import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Resolve, RouterStateSnapshot, Routes} from "@angular/router";

import {Principal, UserRouteAccessService} from "../../shared";
import {PaginationUtil} from "ng-jhipster";

import {CompanyComponent} from "./company.component";
import {CompanyDetailComponent} from "./company-detail.component";
import {CompanyPopupComponent} from "./company-dialog.component";
import {CompanyDeletePopupComponent} from "./company-delete-dialog.component";

@Injectable()
export class CompanyResolve implements CanActivate {

    constructor(private principal: Principal) {
    }

    canActivate() {
        return this.principal.identity().then(account => this.principal.hasAnyAuthority(['ROLE_ADMIN', 'ROLE_MANAGER']));
    }
}

@Injectable()
export class CompanyResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {
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

export const companyRoute: Routes = [
    {
        path: 'company',
        component: CompanyComponent,
        resolve: {
            'pagingParams': CompanyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'logisticsApp.company.home.title'
        },
        canActivate: [CompanyResolve],
    }, {
        path: 'company/:id',
        component: CompanyDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'logisticsApp.company.home.title'
        },
        canActivate: [CompanyResolve],
    }
];

export const companyPopupRoute: Routes = [
    {
        path: 'company-new',
        component: CompanyPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'logisticsApp.company.home.title'
        },
        canActivate: [CompanyResolve],
        outlet: 'popup'
    },
    {
        path: 'company/:id/edit',
        component: CompanyPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'logisticsApp.company.home.title'
        },
        canActivate: [CompanyResolve],
        outlet: 'popup'
    },
    {
        path: 'company/:id/delete',
        component: CompanyDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'logisticsApp.company.home.title'
        },
        canActivate: [CompanyResolve],
        outlet: 'popup'
    }
];
