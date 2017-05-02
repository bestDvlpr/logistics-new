import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LogisticsSharedModule} from '../../shared';

import {
    ReceiptService,
    UploadService,
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
    ReceiptAppliedComponent,
    ReceiptProductToCarComponent,
    ReceiptSendAddressComponent,
    ReceiptArchivedComponent,
    ReceiptCreditComponent,
} from './';
import {AutocompleteModule} from '../../shared/autocomplete/autocomplete.module';

let ENTITY_STATES = [
    ...receiptRoute,
    ...receiptPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true}),
        AutocompleteModule
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
        ReceiptAppliedComponent,
        ReceiptProductToCarComponent,
        ReceiptArchivedComponent,
        ReceiptCreditComponent
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
        ReceiptAppliedComponent,
        ReceiptProductToCarComponent,
        ReceiptArchivedComponent,
        ReceiptCreditComponent
    ],
    providers: [
        ReceiptService,
        UploadService,
        ReceiptPopupService,
        DataHolderService,
        ReceiptResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsReceiptModule {
}
