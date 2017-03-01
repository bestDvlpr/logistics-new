import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { CarColor } from './car-color.model';
import { CarColorService } from './car-color.service';

@Component({
    selector: 'jhi-car-color-detail',
    templateUrl: './car-color-detail.component.html'
})
export class CarColorDetailComponent implements OnInit, OnDestroy {

    carColor: CarColor;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private carColorService: CarColorService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['carColor']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.carColorService.find(id).subscribe(carColor => {
            this.carColor = carColor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
