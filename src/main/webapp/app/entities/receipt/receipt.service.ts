import {Injectable} from '@angular/core';
import {Http, Response, URLSearchParams, BaseRequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Receipt} from './receipt.model';
@Injectable()
export class ReceiptService {

    private resourceUrl = 'api/receipts';
    public _client;
    public _receipt;

    constructor(private http: Http) {
    }

    create(receipt: Receipt): Observable<Receipt> {
        let copy: Receipt = Object.assign({}, receipt);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(receipt: Receipt): Observable<Receipt> {
        let copy: Receipt = Object.assign({}, receipt);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Receipt> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    newReceipts(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/new'), options);
    }

    appliedReceipts(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/applied'), options);
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }

    sendOrder(_receipt: Receipt): Observable<Receipt> {
        let copy: Receipt = Object.assign({}, _receipt);
        return this.http.post(this.resourceUrl.concat('/order'), copy).map((res: Response) => {
            return res.json();
        });
    }

    countNewApplications(): Observable<number> {
        return this.http.get(this.resourceUrl.concat('/count/new')).map((res: Response) => {
            return res.json();
        });
    }

    countAppliedApplications(): Observable<number> {
        return this.http.get(this.resourceUrl.concat('/count/applied')).map((res: Response) => {
            return res.json();
        });
    }

    attachDrivers(receipt: Receipt): Observable<Receipt> {
        let copy: Receipt = Object.assign({}, receipt);
        return this.http.post(this.resourceUrl.concat('/attached'), copy).map((res: Response) => {
            return res.json();
        });
    }
}
