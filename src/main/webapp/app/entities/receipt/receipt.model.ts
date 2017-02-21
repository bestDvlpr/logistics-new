const enum DocType {
    'RETURN',
    'SALES'
};
export class Receipt {
    constructor(
        public id?: number,
        public docNum?: string,
        public docID?: string,
        public docType?: DocType,
        public previousDocID?: string,
        public docDate?: number,
        public payMasterId?: number,
        public loyaltyCardId?: number,
        public productId?: number,
        public payTypeId?: number,
    ) { }
}
