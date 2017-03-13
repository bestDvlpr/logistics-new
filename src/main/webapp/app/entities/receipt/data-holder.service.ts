import {Receipt} from './receipt.model';
import {Client} from '../client/client.model';
import {Injectable} from '@angular/core';
/**
 * Created by hasan on 3/11/17.
 */
@Injectable()
export class DataHolderService{
    _receipt: Receipt;
    _client: Client;
}
