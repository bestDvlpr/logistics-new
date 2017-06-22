import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Company} from "./company.model";
import {CompanyService} from "./company.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-company-detail',
    templateUrl: './company-detail.component.html'
})
export class CompanyDetailComponent implements OnInit, OnDestroy {

    company: Company;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private companyService: CompanyService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    load(id) {
        this.companyService.find(id).subscribe(company => {
            this.company = company;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
