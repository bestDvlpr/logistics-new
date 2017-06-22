import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    PayMasterComponent,
    PayMasterDeleteDialogComponent,
    PayMasterDeletePopupComponent,
    PayMasterDetailComponent,
    PayMasterDialogComponent,
    PayMasterPopupComponent,
    payMasterPopupRoute,
    PayMasterPopupService,
    PayMasterResolvePagingParams,
    payMasterRoute,
    PayMasterService
} from "./";

let ENTITY_STATES = [
    ...payMasterRoute,
    ...payMasterPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PayMasterComponent,
        PayMasterDetailComponent,
        PayMasterDialogComponent,
        PayMasterDeleteDialogComponent,
        PayMasterPopupComponent,
        PayMasterDeletePopupComponent,
    ],
    entryComponents: [
        PayMasterComponent,
        PayMasterDialogComponent,
        PayMasterPopupComponent,
        PayMasterDeleteDialogComponent,
        PayMasterDeletePopupComponent,
    ],
    providers: [
        PayMasterService,
        PayMasterPopupService,
        PayMasterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsPayMasterModule {}
