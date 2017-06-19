import "./vendor.ts";

import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {Ng2Webstorage} from "ng2-webstorage";

import {LogisticsSharedModule, UserRouteAccessService} from "./shared";
import {LogisticsAdminModule} from "./admin/admin.module";
import {LogisticsAccountModule} from "./account/account.module";
import {LogisticsEntityModule} from "./entities/entity.module";

import {
    ActiveMenuDirective,
    ErrorComponent,
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    PageRibbonComponent,
    ProfileService
} from "./layouts";
import {HomeComponent} from "./home";
import {customHttpProvider} from "./blocks/interceptor/http.provider";
import {PaginationConfig} from "./blocks/config/uib-pagination.config";
import {ChartModule} from "angular2-highcharts";
import {SidenavComponent} from "./layouts/sidenav/sidenav.component";
import {CollapseDirective} from "./layouts/sidenav/collapse.component";
import {Ng2CompleterModule} from "ng2-completer";
import {FooterComponent} from "./layouts/footer/footer.component";
import {LogisticsReportModule} from "./report/report.module";


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        LogisticsSharedModule,
        LogisticsAdminModule,
        LogisticsAccountModule,
        LogisticsEntityModule,
        Ng2CompleterModule,
        LogisticsReportModule,
        ChartModule.forRoot(require('highcharts'), require('highcharts/modules/exporting'))
    ],
    declarations: [
        JhiMainComponent,
        HomeComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        SidenavComponent,
        CollapseDirective
    ],
    providers: [
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
