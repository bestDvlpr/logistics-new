import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LogisticsSharedModule} from '../shared';

import {HOME_ROUTE, HomeComponent} from './';
import {ChartModule} from 'angular2-highcharts';
import {NguiAutoCompleteModule} from '@ngui/auto-complete';
import {DataHolderService} from '../entities/receipt/data-holder.service';
import {ReceiptService} from '../entities/receipt/receipt.service';
declare const require: any;

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot([HOME_ROUTE], {useHash: true}),
        NguiAutoCompleteModule,
        ChartModule
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
