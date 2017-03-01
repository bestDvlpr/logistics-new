import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    LoyaltyCardService,
    LoyaltyCardPopupService,
    LoyaltyCardComponent,
    LoyaltyCardDetailComponent,
    LoyaltyCardDialogComponent,
    LoyaltyCardPopupComponent,
    LoyaltyCardDeletePopupComponent,
    LoyaltyCardDeleteDialogComponent,
    loyaltyCardRoute,
    loyaltyCardPopupRoute,
    LoyaltyCardResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...loyaltyCardRoute,
    ...loyaltyCardPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LoyaltyCardComponent,
        LoyaltyCardDetailComponent,
        LoyaltyCardDialogComponent,
        LoyaltyCardDeleteDialogComponent,
        LoyaltyCardPopupComponent,
        LoyaltyCardDeletePopupComponent,
    ],
    entryComponents: [
        LoyaltyCardComponent,
        LoyaltyCardDialogComponent,
        LoyaltyCardPopupComponent,
        LoyaltyCardDeleteDialogComponent,
        LoyaltyCardDeletePopupComponent,
    ],
    providers: [
        LoyaltyCardService,
        LoyaltyCardPopupService,
        LoyaltyCardResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsLoyaltyCardModule {}
