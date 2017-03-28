import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    ProductEntryService,
    ProductEntryPopupService,
    ProductEntryComponent,
    ProductEntryDetailComponent,
    ProductEntryDialogComponent,
    ProductEntryPopupComponent,
    ProductEntryDeletePopupComponent,
    ProductEntryDeleteDialogComponent,
    productEntryRoute,
    productEntryPopupRoute,
    ProductEntryResolvePagingParams,
    ProductEntryDeliveryComponent,
} from './';

let ENTITY_STATES = [
    ...productEntryRoute,
    ...productEntryPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductEntryComponent,
        ProductEntryDetailComponent,
        ProductEntryDialogComponent,
        ProductEntryDeleteDialogComponent,
        ProductEntryPopupComponent,
        ProductEntryDeletePopupComponent,
        ProductEntryDeliveryComponent
    ],
    entryComponents: [
        ProductEntryComponent,
        ProductEntryDialogComponent,
        ProductEntryPopupComponent,
        ProductEntryDeleteDialogComponent,
        ProductEntryDeletePopupComponent,
        ProductEntryDeliveryComponent
    ],
    providers: [
        ProductEntryService,
        ProductEntryPopupService,
        ProductEntryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsProductEntryModule {}
