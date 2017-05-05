// import {Injectable} from '@angular/core';
// import {Headers, Http, RequestOptions, Response} from '@angular/http';
// import {Observable} from 'rxjs/Rx';
// import {Receipt} from './receipt.model';
// @Injectable()
// export class UploadService {
//
//     private resourceUrl = 'api/uploads';
//     public _client;
//     public _receipt;
//     private progressObserver: any;
//     private progress: any;
//
//     constructor(private http: Http) {
//         this.progress = Observable.create(observer => {
//             this.progressObserver = observer;
//         }).share();
//     }
//
//     upload(receiptFile: any): Observable<Receipt> {
//         /*return Observable.create(observer => {
//          let formData: FormData = new FormData(), xhr: XMLHttpRequest = new XMLHttpRequest();
//
//          formData.append(receiptFile, receiptFile.name);
//          xhr.onreadystatechange = () => {
//          if (xhr.readyState === 4) {
//          if (xhr.status === 200) {
//          observer.next(JSON.parse(xhr.response));
//          observer.complete();
//          } else {
//          observer.error(xhr.response);
//          }
//          }
//          };
//
//          xhr.upload.onprogress = (event) => {
//          this.progress = Math.round(event.loaded / event.total * 100);
//
//          this.progressObserver.next(this.progress);
//          };
//
//          xhr.open('POST', this.resourceUrl.concat('/credit'), true);
//          xhr.send(formData);
//          }); */
//         /*let headers = new Headers();
//          headers.append('Content-Type', 'multipart/form-data');
//          return this.http.post(this.resourceUrl.concat('/credit'), receiptFile, headers).map((res: Response) => {
//          return res.json();
//          });*/
//         if (receiptFile) {
//             let formData: FormData = new FormData();
//             formData.append('file', receiptFile, receiptFile.name);
//             let headers = new Headers();
//             headers.append('Content-Type', 'multipart/form-data');
//             headers.append('Accept', 'application/json');
//             let options = new RequestOptions({headers: headers});
//             this.http.post(this.resourceUrl.concat('/credit'), formData, options)
//                 .map((res: Response) => res.json())
//                 .catch((error) => Observable.throw(error))
//                 .subscribe(
//                     (data) => {
//                         return data.json();
//                     },
//                     (error) => {
//                         return error;
//                     }
//                 );
//         } else {
//             return null;
//         }
//     }
// }
/*import {Injectable} from 'angular2/core';
 import {Observable} from 'rxjs/Rx';

 @Injectable()
 export class UploadService {
 constructor () {
 this.progress$ = Observable.create(observer => {
 this.progressObserver = observer
 }).share();
 }

 private makeFileRequest (url: string, params: string[], files: File[]): Observable {
 return Observable.create(observer => {
 let formData: FormData = new FormData(),
 xhr: XMLHttpRequest = new XMLHttpRequest();

 for (let i = 0; i < files.length; i++) {
 formData.append("uploads[]", files[i], files[i].name);
 }

 xhr.onreadystatechange = () => {
 if (xhr.readyState === 4) {
 if (xhr.status === 200) {
 observer.next(JSON.parse(xhr.response));
 observer.complete();
 } else {
 observer.error(xhr.response);
 }
 }
 };

 xhr.upload.onprogress = (event) => {
 this.progress = Math.round(event.loaded / event.total * 100);

 this.progressObserver.next(this.progress);
 };

 xhr.open('POST', url, true);
 xhr.send(formData);
 });
 }
 }*/

import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {Headers, Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
declare var $: any;

@Injectable()
export class UploadService {
    requestUrl = 'api/uploads';
    responseData: any;
    handleError: any;

    constructor(private router: Router,
                private http: Http,) {
        this.http = http;
    }

    upload(postData: any, file: File): Observable<Response> {
        let headers = new Headers();
        headers.append('Content-Type', 'multipart/form-data');
        headers.append('Accept', 'application/json');

        let formData: FormData = new FormData();
        formData.append('file', file, file.name);

        // For multiple files
        // for (let i = 0; i < files.length; i++) {
        //     formData.append(`files[]`, files[i], files[i].name);
        // }

        if (postData !== '' && postData !== undefined && postData !== null) {
            for (let property in postData) {
                if (postData.hasOwnProperty(property)) {
                    formData.append(property, postData[property]);
                }
            }
        }
        return this.http.post(this.requestUrl.concat('/credit'), formData).map((res: Response) => {
            return res;
        });
        // return returnResponse;
    }
}
