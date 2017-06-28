import './vendor.ts';

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {Ng2Webstorage} from 'ng2-webstorage';

import {LogisticsSharedModule, UserRouteAccessService} from './shared';
import {LogisticsHomeModule} from './home/home.module';
import {LogisticsAdminModule} from './admin/admin.module';
import {LogisticsAccountModule} from './account/account.module';
import {LogisticsEntityModule} from './entities/entity.module';

import {customHttpProvider} from './blocks/interceptor/http.provider';
import {PaginationConfig} from './blocks/config/uib-pagination.config';
import {
    ActiveMenuDirective,
    ErrorComponent,
    FooterComponent,
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    PageRibbonComponent,
    ProfileService
} from './layouts';
import {SidenavComponent} from './layouts/sidenav/sidenav.component';
import {CollapseDirective} from './layouts/sidenav/collapse.component';
import {Ng2CompleterModule} from 'ng2-completer';
import {LogisticsReportModule} from './report/report.module';
import {HighchartsStatic} from 'angular2-highcharts/dist/HighchartsService';
// jhipster-needle-angular-add-module-import JHipster will add new module here

export function highchartsFactory() {
    const hc = require('highcharts');
    const hce = require('highcharts/modules/exporting');
    hce(hc);
    return hc;
}
@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        LogisticsSharedModule,
        LogisticsHomeModule,
        LogisticsAdminModule,
        LogisticsAccountModule,
        LogisticsEntityModule,
        Ng2CompleterModule,
        LogisticsReportModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        SidenavComponent,
        CollapseDirective
    ],
    providers: [
        {
            provide: HighchartsStatic,
            useFactory: highchartsFactory
        },
        ProfileService,
        {provide: Window, useValue: window},
        {provide: Document, useValue: document},
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [JhiMainComponent]
})
export class LogisticsAppModule {
}
