import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LogisticsSharedModule} from '../../shared';

import {
    DriverComponent,
    DriverDeleteDialogComponent,
    DriverDeletePopupComponent,
    DriverDetailComponent,
    DriverDialogComponent,
    DriverPopupComponent,
    driverPopupRoute,
    DriverPopupService,
    DriverResolvePagingParams,
    driverRoute,
    DriverService
} from './';

const ENTITY_STATES = [
    ...driverRoute,
    ...driverPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
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
export class LogisticsDriverModule {
}
