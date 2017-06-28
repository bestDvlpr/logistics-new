import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {XmlHolder} from './xml-holder.model';
import {XmlHolderService} from './xml-holder.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-xml-holder-detail',
    templateUrl: './xml-holder-detail.component.html'
})
export class XmlHolderDetailComponent implements OnInit, OnDestroy {

    xmlHolder: XmlHolder;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private xmlHolderService: XmlHolderService,
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
        this.xmlHolderService.find(id).subscribe((xmlHolder) => {
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
