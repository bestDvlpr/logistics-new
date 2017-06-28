import {Component, OnInit} from '@angular/core';
import {JhiLanguageHelper} from '../shared/language/language.helper';
/**
 * @author: hasan @date: 6/3/17.
 */
@Component({
    selector: 'jhi-report',
    templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit {

    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper) {
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }
}
