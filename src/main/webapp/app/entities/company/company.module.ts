import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    CompanyComponent,
    CompanyDeleteDialogComponent,
    CompanyDeletePopupComponent,
    CompanyDetailComponent,
    CompanyDialogComponent,
    CompanyPopupComponent,
    companyPopupRoute,
    CompanyPopupService,
    CompanyResolve,
    CompanyResolvePagingParams,
    companyRoute,
    CompanyService
} from "./";

let ENTITY_STATES = [
    ...companyRoute,
    ...companyPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyComponent,
        CompanyDetailComponent,
        CompanyDialogComponent,
        CompanyDeleteDialogComponent,
        CompanyPopupComponent,
        CompanyDeletePopupComponent,
    ],
    entryComponents: [
        CompanyComponent,
        CompanyDialogComponent,
        CompanyPopupComponent,
        CompanyDeleteDialogComponent,
        CompanyDeletePopupComponent,
    ],
    providers: [
        CompanyService,
        CompanyPopupService,
        CompanyResolvePagingParams,
        CompanyResolve
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsCompanyModule {}
