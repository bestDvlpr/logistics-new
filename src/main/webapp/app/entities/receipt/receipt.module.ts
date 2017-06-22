import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    CollapsedReceiptComponent,
    DataHolderService,
    ReceiptAppliedComponent,
    ReceiptArchivedComponent,
    ReceiptCancelCarDialogComponent,
    ReceiptCancelCarPopupComponent,
    ReceiptComponent,
    ReceiptCorporateComponent,
    ReceiptCreditComponent,
    ReceiptDeleteDialogComponent,
    ReceiptDeletePopupComponent,
    ReceiptDeliveryDialogComponent,
    ReceiptDeliveryPopupComponent,
    ReceiptDetailComponent,
    ReceiptDialogComponent,
    ReceiptNewComponent,
    ReceiptPopupComponent,
    receiptPopupRoute,
    ReceiptPopupService,
    ReceiptProductToCarComponent,
    ReceiptResolvePagingParams,
    receiptRoute,
    ReceiptSendAddressComponent,
    ReceiptSendClientComponent,
    ReceiptSendProductComponent,
    ReceiptService,
    UploadService
} from "./";
import {AutocompleteModule} from "../../shared/autocomplete/autocomplete.module";
import {NguiAutoCompleteModule} from "@ngui/auto-complete";

let ENTITY_STATES = [
    ...receiptRoute,
    ...receiptPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true}),
        AutocompleteModule,
        NguiAutoCompleteModule
    ],
    declarations: [
        ReceiptComponent,
        ReceiptDetailComponent,
        ReceiptDialogComponent,
        ReceiptDeleteDialogComponent,
        ReceiptDeletePopupComponent,
        ReceiptCancelCarDialogComponent,
        ReceiptCancelCarPopupComponent,
        ReceiptDeliveryDialogComponent,
        ReceiptDeliveryPopupComponent,
        ReceiptPopupComponent,
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
        ReceiptCancelCarDialogComponent,
        ReceiptCancelCarPopupComponent,
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
