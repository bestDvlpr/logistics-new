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
    ReceiptAppliedComponent,
    ReceiptProductToCarComponent,
    ReceiptSendAddressComponent,
    ReceiptArchivedComponent,
    ReceiptDeliveryDialogComponent,
    ReceiptDeliveryPopupComponent
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
        ReceiptArchivedComponent
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
        ReceiptArchivedComponent
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
