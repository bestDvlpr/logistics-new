import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    CarTypeService,
    CarTypePopupService,
    CarTypeComponent,
    CarTypeDetailComponent,
    CarTypeDialogComponent,
    CarTypePopupComponent,
    CarTypeDeletePopupComponent,
    CarTypeDeleteDialogComponent,
    carTypeRoute,
    carTypePopupRoute,
    CarTypeResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...carTypeRoute,
    ...carTypePopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CarTypeComponent,
        CarTypeDetailComponent,
        CarTypeDialogComponent,
        CarTypeDeleteDialogComponent,
        CarTypePopupComponent,
        CarTypeDeletePopupComponent,
    ],
    entryComponents: [
        CarTypeComponent,
        CarTypeDialogComponent,
        CarTypePopupComponent,
        CarTypeDeleteDialogComponent,
        CarTypeDeletePopupComponent,
    ],
    providers: [
        CarTypeService,
        CarTypePopupService,
        CarTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsCarTypeModule {}
