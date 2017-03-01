const enum SalesType {
    'TAKEOUT_IN_TIME',
    'TAKEOUT',
    'DELIVERY'
};
const enum SalesPlace {
    'STORE',
    'WAREHOUSE'
};
const enum DefectFlag {
    'WELL',
    'DEFECTED'
};
const enum VirtualFlag {
    'SOLD_PHISICALLY',
    'SOLD_VIRTUALLY'
};
export class ProductEntry {
    constructor(
        public id?: number,
        public price?: number,
        public deliveryFlag?: SalesType,
        public hallFlag?: SalesPlace,
        public defectFlag?: DefectFlag,
        public virtualFlag?: VirtualFlag,
        public reason?: string,
        public comment?: string,
        public guid?: string,
        public qty?: number,
        public discount?: number,
        public productId?: number,
        public sellerIDId?: number,
        public receiptId?: number,
        public driverId?: number,
    ) { }
}
