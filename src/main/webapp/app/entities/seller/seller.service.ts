import {Injectable} from '@angular/core';
import {BaseRequestOptions, Http, Response, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Seller} from './seller.model';
@Injectable()
export class SellerService {

    private resourceUrl = 'api/sellers';

    constructor(private http: Http) {
    }

    create(seller: Seller): Observable<Seller> {
        const copy: Seller = Object.assign({}, seller);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(seller: Seller): Observable<Seller> {
        const copy: Seller = Object.assign({}, seller);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Seller> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
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
}
