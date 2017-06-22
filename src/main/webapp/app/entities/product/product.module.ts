import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    ProductComponent,
    ProductDeleteDialogComponent,
    ProductDeletePopupComponent,
    ProductDetailComponent,
    ProductDialogComponent,
    ProductPopupComponent,
    productPopupRoute,
    ProductPopupService,
    ProductResolvePagingParams,
    productRoute,
    ProductService
} from "./";

let ENTITY_STATES = [
    ...productRoute,
    ...productPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductComponent,
        ProductDetailComponent,
        ProductDialogComponent,
        ProductDeleteDialogComponent,
        ProductPopupComponent,
        ProductDeletePopupComponent,
    ],
    entryComponents: [
        ProductComponent,
        ProductDialogComponent,
        ProductPopupComponent,
        ProductDeleteDialogComponent,
        ProductDeletePopupComponent,
    ],
    providers: [
        ProductService,
        ProductPopupService,
        ProductResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsProductModule {}
