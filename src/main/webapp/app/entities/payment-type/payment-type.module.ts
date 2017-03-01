import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    PaymentTypeService,
    PaymentTypePopupService,
    PaymentTypeComponent,
    PaymentTypeDetailComponent,
    PaymentTypeDialogComponent,
    PaymentTypePopupComponent,
    PaymentTypeDeletePopupComponent,
    PaymentTypeDeleteDialogComponent,
    paymentTypeRoute,
    paymentTypePopupRoute,
    PaymentTypeResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...paymentTypeRoute,
    ...paymentTypePopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaymentTypeComponent,
        PaymentTypeDetailComponent,
        PaymentTypeDialogComponent,
        PaymentTypeDeleteDialogComponent,
        PaymentTypePopupComponent,
        PaymentTypeDeletePopupComponent,
    ],
    entryComponents: [
        PaymentTypeComponent,
        PaymentTypeDialogComponent,
        PaymentTypePopupComponent,
        PaymentTypeDeleteDialogComponent,
        PaymentTypeDeletePopupComponent,
    ],
    providers: [
        PaymentTypeService,
        PaymentTypePopupService,
        PaymentTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsPaymentTypeModule {}
