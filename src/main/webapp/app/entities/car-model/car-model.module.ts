import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    CarModelService,
    CarModelPopupService,
    CarModelComponent,
    CarModelDetailComponent,
    CarModelDialogComponent,
    CarModelPopupComponent,
    CarModelDeletePopupComponent,
    CarModelDeleteDialogComponent,
    carModelRoute,
    carModelPopupRoute,
    CarModelResolvePagingParams,
} from './';

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
