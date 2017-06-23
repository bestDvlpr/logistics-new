import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {CarModel} from "./car-model.model";
import {CarModelService} from "./car-model.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-car-model-detail',
    templateUrl: './car-model-detail.component.html'
})
export class CarModelDetailComponent implements OnInit, OnDestroy {

    carModel: CarModel;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private carModelService: CarModelService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    load(id) {
        this.carModelService.find(id).subscribe((carModel) => {
            this.carModel = carModel;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
