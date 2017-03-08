import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticsSharedModule } from '../../shared';

import {
    XmlHolderService,
    XmlHolderPopupService,
    XmlHolderComponent,
    XmlHolderDetailComponent,
    XmlHolderDialogComponent,
    XmlHolderPopupComponent,
    XmlHolderDeletePopupComponent,
    XmlHolderDeleteDialogComponent,
    xmlHolderRoute,
    xmlHolderPopupRoute,
    XmlHolderResolvePagingParams,
} from './';

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
