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
    ReceiptDeliveryDialogComponent,
    ReceiptDeliveryPopupComponent,
    ReceiptCreditComponent,
    ReceiptCorporateComponent,
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
        ReceiptDeliveryDialogComponent,
        ReceiptDeliveryPopupComponent,
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
        ReceiptCreditComponent,
        ReceiptCorporateComponent
    ],
    entryComponents: [
        ReceiptComponent,
        ReceiptDialogComponent,
        ReceiptPopupComponent,
        ReceiptDeleteDialogComponent,
        ReceiptDeletePopupComponent,
        ReceiptDeliveryDialogComponent,
        ReceiptDeliveryPopupComponent,
        ReceiptSendClientComponent,
        ReceiptSendAddressComponent,
        ReceiptSendProductComponent,
        CollapsedReceiptComponent,
        ReceiptNewComponent,
        ReceiptAppliedComponent,
        ReceiptProductToCarComponent,
        ReceiptArchivedComponent,
        ReceiptCreditComponent,
        ReceiptCorporateComponent
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
