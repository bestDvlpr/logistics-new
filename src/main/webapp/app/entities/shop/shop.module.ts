import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    ShopComponent,
    ShopDeleteDialogComponent,
    ShopDeletePopupComponent,
    ShopDetailComponent,
    ShopDialogComponent,
    ShopPopupComponent,
    shopPopupRoute,
    ShopPopupService,
    ShopResolvePagingParams,
    shopRoute,
    ShopService
} from "./";

let ENTITY_STATES = [
    ...shopRoute,
    ...shopPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShopComponent,
        ShopDetailComponent,
        ShopDialogComponent,
        ShopDeleteDialogComponent,
        ShopPopupComponent,
        ShopDeletePopupComponent,
    ],
    entryComponents: [
        ShopComponent,
        ShopDialogComponent,
        ShopPopupComponent,
        ShopDeleteDialogComponent,
        ShopDeletePopupComponent,
    ],
    providers: [
        ShopService,
        ShopPopupService,
        ShopResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsShopModule {}
