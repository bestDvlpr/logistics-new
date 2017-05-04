/**
 * Created by hasan on 5/3/17.
 */
import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { Http, Response} from '@angular/http';
import {Observable} from "rxjs/Observable";

@Component({
    selector: 'file-upload',
    template: '<input type="file" [multiple]="multiple" #fileInput>'
})
export class FileUploadComponent {
    @Input() multiple: boolean = false;
    @ViewChild('fileInput') inputEl: ElementRef;

    constructor(private http: Http) {}

    upload(): Observable<Response> {
        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();
        if (fileCount > 0) { // a file was selected
            for (let i = 0; i < fileCount; i++) {
                let file = inputEl.files.item(i);
                formData.append('file', file, file.name);
            }
            this.http.post('api/uploads/credit', formData).subscribe(
                (res: Response) => {
                 return res;
                },
                (error) => {
                    return error;
                }
            );
            // do whatever you do...
            // subscribe to observable to listen for response
        } else {
            return null;
        }
    }
}
