/**
 * @author: hasan @date: 6/3/17.
 */

import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {ReportResolve, ReportResolvePagingParams, reportRoute} from "./report.route";
import {ReportComponent} from "./report.component";
import {OverallReportComponent} from "./overall/overall.report.component";
import {ReportService} from "./report.service";
import {BrowserModule} from "@angular/platform-browser";
import {LogisticsSharedModule} from "../shared/shared.module";
import {NguiAutoCompleteModule} from "@ngui/auto-complete";

let ENTITY_STATES = [
    ...reportRoute
];
@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true}),
        BrowserModule,
        NguiAutoCompleteModule
    ],
    declarations: [
        ReportComponent,
        OverallReportComponent
    ],
    entryComponents: [
        ReportComponent
    ],
    providers: [
        ReportService,
        ReportResolve,
        ReportResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsReportModule {
}
