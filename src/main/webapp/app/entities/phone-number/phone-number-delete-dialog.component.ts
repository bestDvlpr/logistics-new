import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {PhoneNumber} from "./phone-number.model";
import {PhoneNumberPopupService} from "./phone-number-popup.service";
import {PhoneNumberService} from "./phone-number.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-phone-number-delete-dialog',
    templateUrl: './phone-number-delete-dialog.component.html'
})
export class PhoneNumberDeleteDialogComponent implements OnInit {
    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    phoneNumber: PhoneNumber;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private phoneNumberService: PhoneNumberService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.phoneNumberService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'phoneNumberListModification',
                content: 'Deleted an phoneNumber'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-phone-number-delete-popup',
    template: ''
})
export class PhoneNumberDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private phoneNumberPopupService: PhoneNumberPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.phoneNumberPopupService
                .open(PhoneNumberDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
