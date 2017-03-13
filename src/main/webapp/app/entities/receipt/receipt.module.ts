import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LogisticsSharedModule} from '../../shared';

import {
    ReceiptService,
    ReceiptPopupService,
    DataHolderService,
    ReceiptComponent,
    ReceiptDetailComponent,
    ReceiptDialogComponent,
    ReceiptPopupComponent,
    ReceiptDeletePopupComponent,
    ReceiptDeleteDialogComponent,
    receiptRoute,
    receiptPopupRoute,
    ReceiptResolvePagingParams,
    ReceiptSendComponent,
} from './';
import {ReceiptSendClientComponent} from "./receipt-send-client.component";

let ENTITY_STATES = [
    ...receiptRoute,
    ...receiptPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
    ],
    declarations: [
        ReceiptComponent,
        ReceiptDetailComponent,
        ReceiptDialogComponent,
        ReceiptDeleteDialogComponent,
        ReceiptPopupComponent,
        ReceiptDeletePopupComponent,
        ReceiptSendComponent,
        ReceiptSendClientComponent,
    ],
    entryComponents: [
        ReceiptComponent,
        ReceiptDialogComponent,
        ReceiptPopupComponent,
        ReceiptDeleteDialogComponent,
        ReceiptDeletePopupComponent,
    ],
    providers: [
        ReceiptService,
        ReceiptPopupService,
        DataHolderService,
        ReceiptResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsReceiptModule {
}
