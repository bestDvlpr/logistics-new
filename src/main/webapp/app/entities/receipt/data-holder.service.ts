import {Receipt} from './receipt.model';
import {Client} from '../client/client.model';
import {Injectable, OnInit} from '@angular/core';
import {Address} from '../address/address.model';
import {ProductEntry} from '../product-entry/product-entry.model';
/**
 * Created by hasan on 3/11/17.
 */
@Injectable()
export class DataHolderService implements OnInit {
    _receipt: Receipt = null;
    _client: Client = null;
    _address: Address = null;
    _selectedProducts: ProductEntry[] = null;
    productCarExists: boolean = false;

    public clearAll() {
        this._receipt = null;
        this._client = null;
        this._address = null;
        this._selectedProducts = null;
    }

    ngOnInit(): void {
        if (this._receipt != null &&
            this._receipt.productEntries != null &&
            this._receipt.productEntries.length > 0) {
            for (let entry of this._receipt.productEntries) {
                if (entry.attachedCarId != null) {
                    this.productCarExists = true;
                }
            }
        }
    }
}
