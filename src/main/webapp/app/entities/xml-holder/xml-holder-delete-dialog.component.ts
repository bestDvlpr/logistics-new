import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {XmlHolder} from './xml-holder.model';
import {XmlHolderPopupService} from './xml-holder-popup.service';
import {XmlHolderService} from './xml-holder.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-xml-holder-delete-dialog',
    templateUrl: './xml-holder-delete-dialog.component.html'
})
export class XmlHolderDeleteDialogComponent implements OnInit {

    xmlHolder: XmlHolder;
    languages: any[];

    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    constructor(private languageHelper: JhiLanguageHelper,
                private xmlHolderService: XmlHolderService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.xmlHolderService.delete(id).subscribe(() => {
            this.eventManager.broadcast({
                name: 'xmlHolderListModification',
                content: 'Deleted an xmlHolder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-xml-holder-delete-popup',
    template: ''
})
export class XmlHolderDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private xmlHolderPopupService: XmlHolderPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.xmlHolderPopupService
                .open(XmlHolderDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
