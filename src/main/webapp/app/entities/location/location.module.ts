import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    LocationService,
    LocationPopupService,
    LocationComponent,
    LocationDetailComponent,
    LocationDialogComponent,
    LocationPopupComponent,
    LocationDeletePopupComponent,
    LocationDeleteDialogComponent,
    locationRoute,
    locationPopupRoute,
    LocationResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...locationRoute,
    ...locationPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LocationComponent,
        LocationDetailComponent,
        LocationDialogComponent,
        LocationDeleteDialogComponent,
        LocationPopupComponent,
        LocationDeletePopupComponent,
    ],
    entryComponents: [
        LocationComponent,
        LocationDialogComponent,
        LocationPopupComponent,
        LocationDeleteDialogComponent,
        LocationDeletePopupComponent,
    ],
    providers: [
        LocationService,
        LocationPopupService,
        LocationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsLocationModule {}
