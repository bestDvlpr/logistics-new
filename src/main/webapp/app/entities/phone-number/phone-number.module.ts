import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    PhoneNumberService,
    PhoneNumberPopupService,
    PhoneNumberComponent,
    PhoneNumberDetailComponent,
    PhoneNumberDialogComponent,
    PhoneNumberPopupComponent,
    PhoneNumberDeletePopupComponent,
    PhoneNumberDeleteDialogComponent,
    phoneNumberRoute,
    phoneNumberPopupRoute,
    PhoneNumberResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...phoneNumberRoute,
    ...phoneNumberPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
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
export class LogisticsPhoneNumberModule {}
