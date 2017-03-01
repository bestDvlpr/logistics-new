import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    ReceiptStatusService,
    ReceiptStatusPopupService,
    ReceiptStatusComponent,
    ReceiptStatusDetailComponent,
    ReceiptStatusDialogComponent,
    ReceiptStatusPopupComponent,
    ReceiptStatusDeletePopupComponent,
    ReceiptStatusDeleteDialogComponent,
    receiptStatusRoute,
    receiptStatusPopupRoute,
    ReceiptStatusResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...receiptStatusRoute,
    ...receiptStatusPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReceiptStatusComponent,
        ReceiptStatusDetailComponent,
        ReceiptStatusDialogComponent,
        ReceiptStatusDeleteDialogComponent,
        ReceiptStatusPopupComponent,
        ReceiptStatusDeletePopupComponent,
    ],
    entryComponents: [
        ReceiptStatusComponent,
        ReceiptStatusDialogComponent,
        ReceiptStatusPopupComponent,
        ReceiptStatusDeleteDialogComponent,
        ReceiptStatusDeletePopupComponent,
    ],
    providers: [
        ReceiptStatusService,
        ReceiptStatusPopupService,
        ReceiptStatusResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsReceiptStatusModule {}
