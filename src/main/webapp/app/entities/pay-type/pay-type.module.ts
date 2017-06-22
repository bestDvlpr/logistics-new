import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    PayTypeComponent,
    PayTypeDeleteDialogComponent,
    PayTypeDeletePopupComponent,
    PayTypeDetailComponent,
    PayTypeDialogComponent,
    PayTypePopupComponent,
    payTypePopupRoute,
    PayTypePopupService,
    PayTypeResolvePagingParams,
    payTypeRoute,
    PayTypeService
} from "./";

let ENTITY_STATES = [
    ...payTypeRoute,
    ...payTypePopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PayTypeComponent,
        PayTypeDetailComponent,
        PayTypeDialogComponent,
        PayTypeDeleteDialogComponent,
        PayTypePopupComponent,
        PayTypeDeletePopupComponent,
    ],
    entryComponents: [
        PayTypeComponent,
        PayTypeDialogComponent,
        PayTypePopupComponent,
        PayTypeDeleteDialogComponent,
        PayTypeDeletePopupComponent,
    ],
    providers: [
        PayTypeService,
        PayTypePopupService,
        PayTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsPayTypeModule {}
