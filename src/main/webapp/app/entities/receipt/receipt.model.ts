export enum DocType {
    'RETURN',
    'SALES'
}
export enum WholeSaleFlag {
    'RETAIL',
    'WHOLESALE'
}
export function WholeSaleFlagAware(constructor: Function) {
    constructor.prototype.WholeSaleFlag = WholeSaleFlag;
    constructor.prototype.DocType = DocType;
}
export class Receipt {
    constructor(
        public id?: number,
        public docNum?: string,
        public docID?: string,
        public docType?: DocType,
        public previousDocID?: string,
        public docDate?: number,
        public wholeSaleFlag?: WholeSaleFlag,
        public payMasterId?: number,
        public loyaltyCardId?: number,
        public statusId?: number,
    ) { }
}
