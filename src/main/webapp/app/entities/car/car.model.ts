import {ProductEntry} from '../product-entry/product-entry.model';
export enum CarStatus {
    IDLE = <any>'IDLE',
    BUSY = <any>'BUSY'

}
export class Car {
    public productEntries: ProductEntry[];

    constructor(public id?: number,
                public number?: string,
                public deleted?: boolean,
                public status?: CarStatus,
                public carModelId?: number,
                public carColorId?: number,
                public typeId?: number) {
        this.deleted = false;
    }
}
