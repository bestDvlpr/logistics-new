const enum DocType {
    'RETURN',
    'SALES'
};
const enum WholeSaleFlag {
    'RETAIL',
    'WHOLESALE'
};
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
        public clientId?: number,
    ) { }
}
