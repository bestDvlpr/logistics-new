import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LogisticsSharedModule} from '../../shared';

import {
    PhoneNumberComponent,
    PhoneNumberDeleteDialogComponent,
    PhoneNumberDeletePopupComponent,
    PhoneNumberDetailComponent,
    PhoneNumberDialogComponent,
    PhoneNumberPopupComponent,
    phoneNumberPopupRoute,
    PhoneNumberPopupService,
    PhoneNumberResolvePagingParams,
    phoneNumberRoute,
    PhoneNumberService
} from './';

const ENTITY_STATES = [
    ...phoneNumberRoute,
    ...phoneNumberPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
    ],
    declarations: [
        PhoneNumberComponent,
        PhoneNumberDetailComponent,
        PhoneNumberDialogComponent,
        PhoneNumberDeleteDialogComponent,
        PhoneNumberPopupComponent,
        PhoneNumberDeletePopupComponent,
    ],
    entryComponents: [
        PhoneNumberComponent,
        PhoneNumberDialogComponent,
        PhoneNumberPopupComponent,
        PhoneNumberDeleteDialogComponent,
        PhoneNumberDeletePopupComponent,
    ],
    providers: [
        PhoneNumberService,
        PhoneNumberPopupService,
        PhoneNumberResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsPhoneNumberModule {
}
