import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Resolve, RouterStateSnapshot, Routes} from "@angular/router";
import {Principal} from "../shared/auth/principal.service";
import {PaginationUtil} from "ng-jhipster";
import {ReportComponent} from "./report.component";
import {OverallReportComponent} from "./overall/overall.report.component";
/**
 * @author: hasan @date: 6/3/17.
 */
@Injectable()
export class ReportResolve implements CanActivate {

    constructor(private principal: Principal) {
    }

    canActivate() {
        return this.principal.identity().then(account => this.principal.hasAnyAuthority(
            [
                'ROLE_ADMIN',
                'ROLE_MANAGER',
                'ROLE_CORPORATE',
                'ROLE_DISPATCHER'
            ]
        ));
    }
}

@Injectable()
export class ReportResolvePagingParams implements Resolve<any> {

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

export const reportRoute: Routes = [
    {
        path: 'report',
        component: ReportComponent,
        resolve: {
            'pagingParams': ReportResolvePagingParams
        },
        data: {
            authorities: ['ROLE_DISPATCHER'],
            pageTitle: 'logisticsApp.report.home.title'
        },
        canActivate: [ReportResolve],
    }, {
        path: 'overall',
        component: OverallReportComponent,
        resolve: {
            'pagingParams': ReportResolvePagingParams
        },
        data: {
            authorities: ['ROLE_DISPATCHER'],
            pageTitle: 'logisticsApp.report.home.title'
        },
        canActivate: [ReportResolve],
    }
];
