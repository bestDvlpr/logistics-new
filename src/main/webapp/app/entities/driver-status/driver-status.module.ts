import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    DriverStatusService,
    DriverStatusPopupService,
    DriverStatusComponent,
    DriverStatusDetailComponent,
    DriverStatusDialogComponent,
    DriverStatusPopupComponent,
    DriverStatusDeletePopupComponent,
    DriverStatusDeleteDialogComponent,
    driverStatusRoute,
    driverStatusPopupRoute,
    DriverStatusResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...driverStatusRoute,
    ...driverStatusPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DriverStatusComponent,
        DriverStatusDetailComponent,
        DriverStatusDialogComponent,
        DriverStatusDeleteDialogComponent,
        DriverStatusPopupComponent,
        DriverStatusDeletePopupComponent,
    ],
    entryComponents: [
        DriverStatusComponent,
        DriverStatusDialogComponent,
        DriverStatusPopupComponent,
        DriverStatusDeleteDialogComponent,
        DriverStatusDeletePopupComponent,
    ],
    providers: [
        DriverStatusService,
        DriverStatusPopupService,
        DriverStatusResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsDriverStatusModule {}
