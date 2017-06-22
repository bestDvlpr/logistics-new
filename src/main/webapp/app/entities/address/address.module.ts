import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    AddressComponent,
    AddressDeleteDialogComponent,
    AddressDeletePopupComponent,
    AddressDetailComponent,
    AddressDialogComponent,
    AddressPopupComponent,
    addressPopupRoute,
    AddressPopupService,
    AddressResolvePagingParams,
    addressRoute,
    AddressService
} from "./";

let ENTITY_STATES = [
    ...addressRoute,
    ...addressPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AddressComponent,
        AddressDetailComponent,
        AddressDialogComponent,
        AddressDeleteDialogComponent,
        AddressPopupComponent,
        AddressDeletePopupComponent,
    ],
    entryComponents: [
        AddressComponent,
        AddressDialogComponent,
        AddressPopupComponent,
        AddressDeleteDialogComponent,
        AddressDeletePopupComponent,
    ],
    providers: [
        AddressService,
        AddressPopupService,
        AddressResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsAddressModule {}
