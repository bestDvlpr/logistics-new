import {Component, OnInit} from "@angular/core";
import {JhiLanguageService} from "ng-jhipster";
/**
 * @author: hasan @date: 6/3/17.
 */
@Component({
    selector: 'jhi-report',
    templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit {

    constructor(private jhiLanguageService: JhiLanguageService) {
        this.jhiLanguageService.setLocations(
            [
                'report'
            ]
        );
    }

    ngOnInit() {

    }
}
