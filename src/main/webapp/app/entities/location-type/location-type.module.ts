import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    LocationTypeService,
    LocationTypePopupService,
    LocationTypeComponent,
    LocationTypeDetailComponent,
    LocationTypeDialogComponent,
    LocationTypePopupComponent,
    LocationTypeDeletePopupComponent,
    LocationTypeDeleteDialogComponent,
    locationTypeRoute,
    locationTypePopupRoute,
    LocationTypeResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...locationTypeRoute,
    ...locationTypePopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LocationTypeComponent,
        LocationTypeDetailComponent,
        LocationTypeDialogComponent,
        LocationTypeDeleteDialogComponent,
        LocationTypePopupComponent,
        LocationTypeDeletePopupComponent,
    ],
    entryComponents: [
        LocationTypeComponent,
        LocationTypeDialogComponent,
        LocationTypePopupComponent,
        LocationTypeDeleteDialogComponent,
        LocationTypeDeletePopupComponent,
    ],
    providers: [
        LocationTypeService,
        LocationTypePopupService,
        LocationTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsLocationTypeModule {}
