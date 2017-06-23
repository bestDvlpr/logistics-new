import {Injectable} from "@angular/core";
import {BaseRequestOptions, Http, Response, ResponseContentType, URLSearchParams} from "@angular/http";
import {Observable} from "rxjs/Rx";

import {DocType, Receipt, ReceiptStatus} from "./receipt.model";
@Injectable()
export class ReceiptService {

    private resourceUrl = 'api/receipts';
    public _client;
    public _receipt;

    constructor(private http: Http) {
    }

    create(receipt: Receipt): Observable<Receipt> {
        const copy: Receipt = Object.assign({}, receipt);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(receipt: Receipt): Observable<Receipt> {
        const copy: Receipt = Object.assign({}, receipt);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Receipt> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    getAll(): Observable<Receipt[]> {
        return this.http.get(this.resourceUrl.concat('/all')).map((res: Response) => {
            return res.json();
        });
    }

    newReceipts(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/new/sales/retail'), options);
    }

    appliedReceipts(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/applied'), options);
    }

    archivedReceipts(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/archived'), options);
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options);
    }

    accepted(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/accepted'), options);
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

    sendOrder(_receipt: Receipt): Observable<Receipt[]> {
        // let copy: Receipt[] = Object.assign({}, _receipt);
        return this.http.post(this.resourceUrl.concat('/order'), _receipt).map((res: Response) => {
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
        const copy: Receipt = Object.assign({}, receipt);
        return this.http.post(this.resourceUrl.concat('/attached'), copy).map((res: Response) => {
            return res.json();
        });
    }

    allByCompanyId(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/by-company-id'), options);
    }

    downloadReceipt(receiptId: number) {
        const mediaType = ResponseContentType.Blob;
        return this.http.get(this.resourceUrl.concat('/sent-receipt/').concat(receiptId + ''), {responseType: mediaType});
    }

    delivered(receipt: Receipt): Observable<Response> {
        return this.http.post(this.resourceUrl.concat('/delivered'), receipt).map((res: Response) => {
            return res;
        });
    }

    takenOut(receipt: Receipt): Observable<Response> {
        return this.http.post(this.resourceUrl.concat('/taken-out'), receipt).map((res: Response) => {
            return res;
        });
    }

    creditedReceipts(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/credit'), options);
    }

    corporateReceipts(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/corporate'), options);
    }

    creditedReceiptsByCompanyId(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl.concat('/credit/by-company-id'), options);
    }

    getByClientId(clientId: number, req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(`${this.resourceUrl.concat('/by-client-id')}/${clientId}`, options);
    }

    uploadDisplacement(formData: FormData): Observable<Response> {
        return this.http.post(this.resourceUrl.concat('/upload/displacement'), formData).map((res: Response) => {
            return res;
        });
    }

    uploadReceipt(formData: FormData, docType: DocType, companyId: string): Observable<Response> {
        return this.http.post(`${this.resourceUrl.concat('/upload')}/${docType}/${companyId}`, formData).map((res: Response) => {
            return res;
        });
    }

    cancelAttachedCar(receiptId: number): Observable<Receipt> {
        return this.http.get(`${this.resourceUrl}/cancel-attached-car/${receiptId}`).map((res: Response) => {
            return res.json();
        });
    }

    /*find(id: number): Observable<Receipt> {
     return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
     return res.json();
     });
     }*/
    report() {
        return this.http.get('api/report/generic').map((res: Response) => {
            return res.json();
        });
    }

    countByStatus(status: ReceiptStatus): Observable<number> {
        return this.http.get(`${this.resourceUrl}/count/by-status/${status}`).map((res: Response) => {
            return res.json();
        });
    }

    countByStatusAndCompanyId(status: ReceiptStatus, companyId: string): Observable<number> {
        return this.http.get(`${this.resourceUrl}/count/by-status/by-company/${status}/${companyId}`).map((res: Response) => {
            return res.json();
        });
    }
}
