import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    CarModelComponent,
    CarModelDeleteDialogComponent,
    CarModelDeletePopupComponent,
    CarModelDetailComponent,
    CarModelDialogComponent,
    CarModelPopupComponent,
    carModelPopupRoute,
    CarModelPopupService,
    CarModelResolvePagingParams,
    carModelRoute,
    CarModelService
} from "./";

let ENTITY_STATES = [
    ...carModelRoute,
    ...carModelPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CarModelComponent,
        CarModelDetailComponent,
        CarModelDialogComponent,
        CarModelDeleteDialogComponent,
        CarModelPopupComponent,
        CarModelDeletePopupComponent,
    ],
    entryComponents: [
        CarModelComponent,
        CarModelDialogComponent,
        CarModelPopupComponent,
        CarModelDeleteDialogComponent,
        CarModelDeletePopupComponent,
    ],
    providers: [
        CarModelService,
        CarModelPopupService,
        CarModelResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsCarModelModule {}
