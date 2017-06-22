import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../shared";

import {HOME_ROUTE, HomeComponent} from "./";
import {ChartModule} from "angular2-highcharts";
import {NguiAutoCompleteModule} from "@ngui/auto-complete";
import {DataHolderService} from "../entities/receipt/data-holder.service";
import {TranslateModule} from "ng2-translate";
import {ReceiptService} from "../entities/receipt/receipt.service";

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot([HOME_ROUTE], {useHash: true}),
        NguiAutoCompleteModule,
        ChartModule.forRoot(require('highcharts'), require('highcharts/modules/exporting')),
        TranslateModule.forRoot()
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [],
    providers: [
        DataHolderService,
        ReceiptService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsHomeModule {
}
