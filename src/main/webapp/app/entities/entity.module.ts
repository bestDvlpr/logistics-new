import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LogisticsReceiptModule } from './receipt/receipt.module';
import { LogisticsPayMasterModule } from './pay-master/pay-master.module';
import { LogisticsLoyaltyCardModule } from './loyalty-card/loyalty-card.module';
import { LogisticsSellerModule } from './seller/seller.module';
import { LogisticsProductModule } from './product/product.module';
import { LogisticsPaymentTypeModule } from './payment-type/payment-type.module';
import { LogisticsPayTypeModule } from './pay-type/pay-type.module';
import { LogisticsCarModelModule } from './car-model/car-model.module';
import { LogisticsCarModule } from './car/car.module';
import { LogisticsDriverModule } from './driver/driver.module';
import { LogisticsCarColorModule } from './car-color/car-color.module';
import { LogisticsCarTypeModule } from './car-type/car-type.module';
import { LogisticsLocationModule } from './location/location.module';
import { LogisticsLocationTypeModule } from './location-type/location-type.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LogisticsReceiptModule,
        LogisticsPayMasterModule,
        LogisticsLoyaltyCardModule,
        LogisticsSellerModule,
        LogisticsProductModule,
        LogisticsPaymentTypeModule,
        LogisticsPayTypeModule,
        LogisticsCarModelModule,
        LogisticsCarModule,
        LogisticsDriverModule,
        LogisticsCarColorModule,
        LogisticsCarTypeModule,
        LogisticsLocationModule,
        LogisticsLocationTypeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsEntityModule {}
