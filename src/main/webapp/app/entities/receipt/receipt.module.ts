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
    ReceiptSendClientComponent,
    ReceiptSendProductComponent,
    CollapsedReceiptComponent,
    ReceiptNewComponent,
} from './';
import {ReceiptSendAddressComponent} from './receipt-send-address.component';

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
        ReceiptSendClientComponent,
        ReceiptSendAddressComponent,
        ReceiptSendProductComponent,
        CollapsedReceiptComponent,
        ReceiptNewComponent,
    ],
    entryComponents: [
        ReceiptComponent,
        ReceiptDialogComponent,
        ReceiptPopupComponent,
        ReceiptDeleteDialogComponent,
        ReceiptDeletePopupComponent,
        ReceiptSendClientComponent,
        ReceiptSendAddressComponent,
        ReceiptSendProductComponent,
        CollapsedReceiptComponent,
        ReceiptNewComponent,
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
