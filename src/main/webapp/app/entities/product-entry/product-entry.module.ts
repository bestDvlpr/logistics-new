import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LogisticsSharedModule} from '../../shared';

import {
    ProductEntryComponent,
    ProductEntryDeleteDialogComponent,
    ProductEntryDeletePopupComponent,
    ProductEntryDeliveryComponent,
    ProductEntryDetailComponent,
    ProductEntryDialogComponent,
    ProductEntryDoneComponent,
    ProductEntryPopupComponent,
    productEntryPopupRoute,
    ProductEntryPopupService,
    ProductEntryResolvePagingParams,
    productEntryRoute,
    ProductEntryService
} from './';

const ENTITY_STATES = [
    ...productEntryRoute,
    ...productEntryPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
    ],
    declarations: [
        ProductEntryComponent,
        ProductEntryDetailComponent,
        ProductEntryDialogComponent,
        ProductEntryDeleteDialogComponent,
        ProductEntryPopupComponent,
        ProductEntryDeletePopupComponent,
        ProductEntryDeliveryComponent,
        ProductEntryDoneComponent
    ],
    entryComponents: [
        ProductEntryComponent,
        ProductEntryDialogComponent,
        ProductEntryPopupComponent,
        ProductEntryDeleteDialogComponent,
        ProductEntryDeletePopupComponent,
        ProductEntryDeliveryComponent,
        ProductEntryDoneComponent
    ],
    providers: [
        ProductEntryService,
        ProductEntryPopupService,
        ProductEntryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsProductEntryModule {
}
