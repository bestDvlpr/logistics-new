import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiLanguageService} from 'ng-jhipster';
import {Car} from './car.model';
import {CarService} from './car.service';

@Component({
    selector: 'jhi-car-delivery-products',
    templateUrl: './car-delivery-products.component.html'
})
export class CarDeliveryProductsComponent implements OnInit, OnDestroy {

    car: Car;
    private subscription: any;

    constructor(private jhiLanguageService: JhiLanguageService,
                private carService: CarService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['car', 'carStatus', 'productEntry', 'salesPlace', 'salesType', 'defectFlag']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load(id) {
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

    /*printBlock(): void {
        let printContents, popupWin;
        printContents = document.getElementById('print-section').innerHTML;
        popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
        popupWin.document.open();
        popupWin.document.write(
            `<html>
                <head>
                    <base href="/" />
                    <meta charset="utf-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <title>Print tab</title>
                    <meta name="description" content="">
                    <meta name="google" value="notranslate">
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                </head>
                <body onload="window.print();window.close()">${printContents}</body>
              </html>`
        );
        popupWin.document.close();
    }*/

}
