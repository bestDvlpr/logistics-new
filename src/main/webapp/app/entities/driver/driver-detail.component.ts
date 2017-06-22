import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Driver} from "./driver.model";
import {DriverService} from "./driver.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-driver-detail',
    templateUrl: './driver-detail.component.html'
})
export class DriverDetailComponent implements OnInit, OnDestroy {

    driver: Driver;
    private subscription: any;
    languages: any[];

    constructor(
        private languageHelper: JhiLanguageHelper,
        private driverService: DriverService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    load (id) {
        this.driverService.find(id).subscribe(driver => {
            this.driver = driver;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
