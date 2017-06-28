import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {LogisticsReceiptModule} from './receipt/receipt.module';
import {LogisticsPayMasterModule} from './pay-master/pay-master.module';
import {LogisticsLoyaltyCardModule} from './loyalty-card/loyalty-card.module';
import {LogisticsSellerModule} from './seller/seller.module';
import {LogisticsProductModule} from './product/product.module';
import {LogisticsPayTypeModule} from './pay-type/pay-type.module';
import {LogisticsCarModelModule} from './car-model/car-model.module';
import {LogisticsCarModule} from './car/car.module';
import {LogisticsDriverModule} from './driver/driver.module';
import {LogisticsCarColorModule} from './car-color/car-color.module';
import {LogisticsCarTypeModule} from './car-type/car-type.module';
import {LogisticsLocationModule} from './location/location.module';
import {LogisticsProductEntryModule} from './product-entry/product-entry.module';
import {LogisticsPhoneNumberModule} from './phone-number/phone-number.module';
import {LogisticsClientModule} from './client/client.module';
import {LogisticsAddressModule} from './address/address.module';
import {LogisticsXmlHolderModule} from './xml-holder/xml-holder.module';
import {LogisticsShopModule} from './shop/shop.module';
import {LogisticsCompanyModule} from './company/company.module';

@NgModule({
    /* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
    imports: [
        LogisticsReceiptModule,
        LogisticsPayMasterModule,
        LogisticsLoyaltyCardModule,
        LogisticsSellerModule,
        LogisticsProductModule,
        LogisticsPayTypeModule,
        LogisticsCarModelModule,
        LogisticsCarModule,
        LogisticsDriverModule,
        LogisticsCarColorModule,
        LogisticsCarTypeModule,
        LogisticsLocationModule,
        LogisticsProductEntryModule,
        LogisticsPhoneNumberModule,
        LogisticsClientModule,
        LogisticsAddressModule,
        LogisticsXmlHolderModule,
        LogisticsShopModule,
        LogisticsCompanyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsEntityModule {
}
