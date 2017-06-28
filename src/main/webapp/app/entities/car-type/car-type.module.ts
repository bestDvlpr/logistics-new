import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LogisticsSharedModule} from '../../shared';

import {
    CarTypeComponent,
    CarTypeDeleteDialogComponent,
    CarTypeDeletePopupComponent,
    CarTypeDetailComponent,
    CarTypeDialogComponent,
    CarTypePopupComponent,
    carTypePopupRoute,
    CarTypePopupService,
    CarTypeResolvePagingParams,
    carTypeRoute,
    CarTypeService
} from './';

const ENTITY_STATES = [
    ...carTypeRoute,
    ...carTypePopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
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
export class LogisticsCarTypeModule {
}
