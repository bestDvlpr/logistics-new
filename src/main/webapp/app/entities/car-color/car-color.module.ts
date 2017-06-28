import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LogisticsSharedModule} from '../../shared';

import {
    CarColorComponent,
    CarColorDeleteDialogComponent,
    CarColorDeletePopupComponent,
    CarColorDetailComponent,
    CarColorDialogComponent,
    CarColorPopupComponent,
    carColorPopupRoute,
    CarColorPopupService,
    CarColorResolvePagingParams,
    carColorRoute,
    CarColorService
} from './';

const ENTITY_STATES = [
    ...carColorRoute,
    ...carColorPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
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
export class LogisticsCarColorModule {
}
