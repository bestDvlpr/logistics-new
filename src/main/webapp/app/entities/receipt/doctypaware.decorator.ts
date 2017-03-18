import {DocType, ReceiptStatus, WholeSaleFlag} from './receipt.model';
/**
 * Created by hasan on 3/3/17.
 */
export function DocTypeEnumAware(constructor: Function) {
    constructor.prototype.DocType = DocType;
    constructor.prototype.WholeSaleFlag = WholeSaleFlag;
    constructor.prototype.ReceiptStatus = ReceiptStatus;
}
