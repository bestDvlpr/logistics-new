import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../../shared";

import {
    XmlHolderComponent,
    XmlHolderDeleteDialogComponent,
    XmlHolderDeletePopupComponent,
    XmlHolderDetailComponent,
    XmlHolderDialogComponent,
    XmlHolderPopupComponent,
    xmlHolderPopupRoute,
    XmlHolderPopupService,
    XmlHolderResolvePagingParams,
    xmlHolderRoute,
    XmlHolderService
} from "./";

let ENTITY_STATES = [
    ...xmlHolderRoute,
    ...xmlHolderPopupRoute,
];

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        XmlHolderComponent,
        XmlHolderDetailComponent,
        XmlHolderDialogComponent,
        XmlHolderDeleteDialogComponent,
        XmlHolderPopupComponent,
        XmlHolderDeletePopupComponent,
    ],
    entryComponents: [
        XmlHolderComponent,
        XmlHolderDialogComponent,
        XmlHolderPopupComponent,
        XmlHolderDeleteDialogComponent,
        XmlHolderDeletePopupComponent,
    ],
    providers: [
        XmlHolderService,
        XmlHolderPopupService,
        XmlHolderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsXmlHolderModule {}
