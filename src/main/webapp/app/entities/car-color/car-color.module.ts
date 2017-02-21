import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    CarColorService,
    CarColorPopupService,
    CarColorComponent,
    CarColorDetailComponent,
    CarColorDialogComponent,
    CarColorPopupComponent,
    CarColorDeletePopupComponent,
    CarColorDeleteDialogComponent,
    carColorRoute,
    carColorPopupRoute,
    CarColorResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...carColorRoute,
    ...carColorPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CarColorComponent,
        CarColorDetailComponent,
        CarColorDialogComponent,
        CarColorDeleteDialogComponent,
        CarColorPopupComponent,
        CarColorDeletePopupComponent,
    ],
    entryComponents: [
        CarColorComponent,
        CarColorDialogComponent,
        CarColorPopupComponent,
        CarColorDeleteDialogComponent,
        CarColorDeletePopupComponent,
    ],
    providers: [
        CarColorService,
        CarColorPopupService,
        CarColorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsCarColorModule {}
