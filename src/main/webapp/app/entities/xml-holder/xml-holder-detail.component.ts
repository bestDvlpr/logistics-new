import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { XmlHolder } from './xml-holder.model';
import { XmlHolderService } from './xml-holder.service';

@Component({
    selector: 'jhi-xml-holder-detail',
    templateUrl: './xml-holder-detail.component.html'
})
export class XmlHolderDetailComponent implements OnInit, OnDestroy {

    xmlHolder: XmlHolder;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private xmlHolderService: XmlHolderService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['xmlHolder']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.xmlHolderService.find(id).subscribe(xmlHolder => {
            this.xmlHolder = xmlHolder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
