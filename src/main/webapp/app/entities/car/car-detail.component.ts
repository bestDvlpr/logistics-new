import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Car} from "./car.model";
import {CarService} from "./car.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-car-detail',
    templateUrl: './car-detail.component.html'
})
export class CarDetailComponent implements OnInit, OnDestroy {

    car: Car;
    private subscription: any;
    languages: any[];

    constructor(
        private languageHelper: JhiLanguageHelper,
        private carService: CarService,
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
        this.carService.find(id).subscribe(car => {
            this.car = car;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
