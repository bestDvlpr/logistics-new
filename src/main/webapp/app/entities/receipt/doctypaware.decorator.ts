import {DocType, ReceiptStatus, WholeSaleFlag} from './receipt.model';
import {CarStatus} from '../car/car.model';
import {DefectFlag, SalesPlace, SalesType, VirtualFlag} from '../product-entry/product-entry.model';
/**
 * Created by hasan on 3/3/17.
 */
export function EnumAware(constructor: Function) {
    constructor.prototype.DocType = DocType;
    constructor.prototype.WholeSaleFlag = WholeSaleFlag;
    constructor.prototype.ReceiptStatus = ReceiptStatus;
    constructor.prototype.CarStatus = CarStatus;
    constructor.prototype.SalesType = SalesType;
    constructor.prototype.SalesPlace = SalesPlace;
    constructor.prototype.DefectFlag = DefectFlag;
    constructor.prototype.VirtualFlag = VirtualFlag;
}
