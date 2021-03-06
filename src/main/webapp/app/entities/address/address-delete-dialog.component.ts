import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {Address} from './address.model';
import {AddressPopupService} from './address-popup.service';
import {AddressService} from './address.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-address-delete-dialog',
    templateUrl: './address-delete-dialog.component.html'
})
export class AddressDeleteDialogComponent implements OnInit {

    address: Address;
    languages: any[];

    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    constructor(private languageHelper: JhiLanguageHelper,
                private addressService: AddressService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.addressService.delete(id).subscribe(() => {
            this.eventManager.broadcast({
                name: 'addressListModification',
                content: 'Deleted an address'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-address-delete-popup',
    template: ''
})
export class AddressDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private addressPopupService: AddressPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.addressPopupService
                .open(AddressDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
