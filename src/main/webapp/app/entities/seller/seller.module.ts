import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    SellerComponent,
    SellerDeleteDialogComponent,
    SellerDeletePopupComponent,
    SellerDetailComponent,
    SellerDialogComponent,
    SellerPopupComponent,
    sellerPopupRoute,
    SellerPopupService,
    SellerResolvePagingParams,
    sellerRoute,
    SellerService
} from "./";

let ENTITY_STATES = [
    ...sellerRoute,
    ...sellerPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SellerComponent,
        SellerDetailComponent,
        SellerDialogComponent,
        SellerDeleteDialogComponent,
        SellerPopupComponent,
        SellerDeletePopupComponent,
    ],
    entryComponents: [
        SellerComponent,
        SellerDialogComponent,
        SellerPopupComponent,
        SellerDeleteDialogComponent,
        SellerDeletePopupComponent,
    ],
    providers: [
        SellerService,
        SellerPopupService,
        SellerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsSellerModule {}
