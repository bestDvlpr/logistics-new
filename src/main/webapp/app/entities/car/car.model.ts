export class Car {
    constructor(
        public id?: number,
        public number?: string,
        public deleted?: boolean,
        public carModelId?: number,
        public carColorId?: number,
        public typeId?: number,
        public driversId?: number,
        public receiptsId?: number ) {
        this.deleted = false;
    }
}
