import {Injectable} from '@angular/core';
import {BaseRequestOptions, Http, Response, ResponseContentType, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {ProductEntry} from './product-entry.model';
@Injectable()
export class ProductEntryService {

    private resourceUrl = 'api/product-entries';

    constructor(private http: Http) {
    }

    create(productEntry: ProductEntry): Observable<ProductEntry> {
        const copy: ProductEntry = Object.assign({}, productEntry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(productEntry: ProductEntry): Observable<ProductEntry> {
        const copy: ProductEntry = Object.assign({}, productEntry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ProductEntry> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            ;
    }

    byReceipt(receiptId: number): Observable<Response> {
        return this.http.get(this.resourceUrl + '/receipt-entries/' + receiptId);
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

    byCarNumber(carNumber: string) {
        return this.http.get(this.resourceUrl + '/car-entries/' + carNumber);
    }

    allByCarNumber(carNumber: string) {
        return this.http.get(this.resourceUrl + '/car-entries-all/' + carNumber);
    }

    productsDelivered(productEntries: ProductEntry[]) {
        return this.http.post(this.resourceUrl + '/delivered', productEntries);
    }

    deliver(productEntries: ProductEntry[]) {
        const mediaType = ResponseContentType.Blob;
        return this.http.post(this.resourceUrl.concat('/delivery'), productEntries, {responseType: mediaType});
    }
}
