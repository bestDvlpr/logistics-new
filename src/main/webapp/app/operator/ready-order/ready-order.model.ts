import {Receipt} from '../../entities/receipt/receipt.model';
import {Client} from '../../entities/client/client.model';
import {Car} from '../../entities/car/car.model';
import {Driver} from '../../entities/driver/driver.model';
import {ProductEntry} from '../../entities/product-entry/product-entry.model';
/**
 * Created by User on 3/9/2017.
 */

export class ReadyOrder {
    constructor(receipt?: Receipt,
                customer?: Client,
                car?: Car,
                driver?: Driver,
                productEntries?: ProductEntry[]) {
    }
}
