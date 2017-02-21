import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    SellerService,
    SellerPopupService,
    SellerComponent,
    SellerDetailComponent,
    SellerDialogComponent,
    SellerPopupComponent,
    SellerDeletePopupComponent,
    SellerDeleteDialogComponent,
    sellerRoute,
    sellerPopupRoute,
    SellerResolvePagingParams,
} from './';

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
