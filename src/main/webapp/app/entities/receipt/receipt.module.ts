import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    ReceiptService,
    ReceiptPopupService,
    ReceiptComponent,
    ReceiptDetailComponent,
    ReceiptDialogComponent,
    ReceiptSendComponent,
    ReceiptPopupComponent,
    ReceiptDeletePopupComponent,
    ReceiptDeleteDialogComponent,
    receiptRoute,
    receiptPopupRoute,
    ReceiptResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...receiptRoute,
    ...receiptPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReceiptComponent,
        ReceiptDetailComponent,
        ReceiptDialogComponent,
        ReceiptSendComponent,
        ReceiptDeleteDialogComponent,
        ReceiptPopupComponent,
        ReceiptDeletePopupComponent,
    ],
    entryComponents: [
        ReceiptComponent,
        ReceiptDialogComponent,
        ReceiptSendComponent,
        ReceiptPopupComponent,
        ReceiptDeleteDialogComponent,
        ReceiptDeletePopupComponent,
    ],
    providers: [
        ReceiptService,
        ReceiptPopupService,
        ReceiptResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsReceiptModule {}
