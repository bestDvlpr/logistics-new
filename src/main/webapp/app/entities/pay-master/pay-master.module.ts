import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    PayMasterService,
    PayMasterPopupService,
    PayMasterComponent,
    PayMasterDetailComponent,
    PayMasterDialogComponent,
    PayMasterPopupComponent,
    PayMasterDeletePopupComponent,
    PayMasterDeleteDialogComponent,
    payMasterRoute,
    payMasterPopupRoute,
    PayMasterResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...payMasterRoute,
    ...payMasterPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PayMasterComponent,
        PayMasterDetailComponent,
        PayMasterDialogComponent,
        PayMasterDeleteDialogComponent,
        PayMasterPopupComponent,
        PayMasterDeletePopupComponent,
    ],
    entryComponents: [
        PayMasterComponent,
        PayMasterDialogComponent,
        PayMasterPopupComponent,
        PayMasterDeleteDialogComponent,
        PayMasterDeletePopupComponent,
    ],
    providers: [
        PayMasterService,
        PayMasterPopupService,
        PayMasterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsPayMasterModule {}
