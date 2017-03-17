import {Receipt} from './receipt.model';
import {Client} from '../client/client.model';
import {Injectable} from '@angular/core';
import {Address} from '../address/address.model';
import {ProductEntry} from '../product-entry/product-entry.model';
/**
 * Created by hasan on 3/11/17.
 */
@Injectable()
export class DataHolderService {
    _receipt: Receipt;
    _client: Client;
    _address: Address;
    _selectedProducts: ProductEntry[];

    public clearAll() {
        this._receipt = null;
        this._client = null;
        this._address = null;
        this._selectedProducts = null;
    }
}
