import {DocType, ReceiptStatus, WholeSaleFlag} from './receipt.model';
import {CarStatus} from '../car/car.model';
/**
 * Created by hasan on 3/3/17.
 */
export function EnumAware(constructor: Function) {
    constructor.prototype.DocType = DocType;
    constructor.prototype.WholeSaleFlag = WholeSaleFlag;
    constructor.prototype.ReceiptStatus = ReceiptStatus;
    constructor.prototype.CarStatus = CarStatus;
}
