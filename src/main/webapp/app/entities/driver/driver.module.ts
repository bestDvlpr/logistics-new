import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    DriverService,
    DriverPopupService,
    DriverComponent,
    DriverDetailComponent,
    DriverDialogComponent,
    DriverPopupComponent,
    DriverDeletePopupComponent,
    DriverDeleteDialogComponent,
    driverRoute,
    driverPopupRoute,
    DriverResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...driverRoute,
    ...driverPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DriverComponent,
        DriverDetailComponent,
        DriverDialogComponent,
        DriverDeleteDialogComponent,
        DriverPopupComponent,
        DriverDeletePopupComponent,
    ],
    entryComponents: [
        DriverComponent,
        DriverDialogComponent,
        DriverPopupComponent,
        DriverDeleteDialogComponent,
        DriverDeletePopupComponent,
    ],
    providers: [
        DriverService,
        DriverPopupService,
        DriverResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsDriverModule {}
