import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

import {Company} from "./company.model";
import {CompanyPopupService} from "./company-popup.service";
import {CompanyService} from "./company.service";
import {Address, AddressService} from "../address";
import {JhiLanguageHelper} from "../../shared/language/language.helper";
@Component({
    selector: 'jhi-company-dialog',
    templateUrl: './company-dialog.component.html'
})
export class CompanyDialogComponent implements OnInit {

    company: Company;
    authorities: any[];
    isSaving: boolean;
    addresses: Address[];
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private companyService: CompanyService,
                private addressService: AddressService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.addressService.getAll().subscribe(
            (res: Address[]) => {
                this.addresses = res;
            }, (res: Response) => this.onError(res.json()));
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.company.id !== undefined) {
            this.companyService.update(this.company)
                .subscribe((res: Company) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.companyService.create(this.company)
                .subscribe((res: Company) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Company) {
        this.eventManager.broadcast({name: 'companyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackAddressById(index: number, item: Address) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-company-popup',
    template: ''
})
export class CompanyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private companyPopupService: CompanyPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.companyPopupService
                    .open(CompanyDialogComponent, params['id']);
            } else {
                this.modalRef = this.companyPopupService
                    .open(CompanyDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
