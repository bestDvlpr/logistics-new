import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    CarService,
    CarPopupService,
    CarComponent,
    CarDetailComponent,
    CarDialogComponent,
    CarPopupComponent,
    CarDeletePopupComponent,
    CarDeleteDialogComponent,
    carRoute,
    carPopupRoute,
    CarResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...carRoute,
    ...carPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CarComponent,
        CarDetailComponent,
        CarDialogComponent,
        CarDeleteDialogComponent,
        CarPopupComponent,
        CarDeletePopupComponent,
    ],
    entryComponents: [
        CarComponent,
        CarDialogComponent,
        CarPopupComponent,
        CarDeleteDialogComponent,
        CarDeletePopupComponent,
    ],
    providers: [
        CarService,
        CarPopupService,
        CarResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsCarModule {}
