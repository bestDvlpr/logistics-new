import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {EventManager, JhiLanguageService} from "ng-jhipster";

import {UserModalService} from "./user-modal.service";
import {JhiLanguageHelper, User, UserService} from "../../shared";
import {Response} from "@angular/http";
import {DataHolderService} from "../../entities/receipt/data-holder.service";
import {ACElement} from "../../shared/autocomplete/element.model";
import {CompanyService} from "../../entities/company/company.service";
import {Company} from "../../entities/company/company.model";

@Component({
    selector: 'jhi-user-mgmt-dialog',
    templateUrl: './user-management-dialog.component.html'
})
export class UserMgmtDialogComponent implements OnInit {

    user: User;
    languages: any[];
    authorities: any[];
    isSaving: Boolean;
    companies: Company[];
    acObjects: ACElement[];
    source: string[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private jhiLanguageService: JhiLanguageService,
                private userService: UserService,
                private companyService: CompanyService,
                private eventManager: EventManager,
                private dataHolderService: DataHolderService) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.loadCompanies();
        this.authorities = [
            'ROLE_USER',
            'ROLE_ADMIN',
            'ROLE_DISPATCHER',
            'ROLE_CASHIER',
            'ROLE_MANAGER',
            'ROLE_CREDIT',
            'ROLE_CORPORATE',
            'ROLE_WAREHOUSE'
        ];
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
        this.jhiLanguageService.setLocations(['user-management', 'company']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.user.companyName) {
            let filter = this.companies.find((x) => x.name === this.user.companyName);
            let company = this.companies[this.companies.indexOf(filter)];
            this.user.companyId = company.id;
            this.user.companyName = company.name;
        }
        if (this.user.id !== null) {
            this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        } else {
            this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        }
    }

    private onSaveSuccess(result) {
        this.eventManager.broadcast({name: 'userListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private loadCompanies() {
        this.companyService.all().subscribe((res: Response) => {
            this.companies = res.json();
            this.setACObjects(this.companies);
        });
    }

    private setACObjects(shops: Company[]) {
        this.dataHolderService.clearAll();
        if (shops !== null && shops.length > 0) {
            this.source = [];
            for (let shop of shops) {
                this.source.push(shop.name);
            }
        }
    }
}

@Component({
    selector: 'jhi-user-dialog',
    template: ''
})
export class UserDialogComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private userModalService: UserModalService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['login']) {
                this.modalRef = this.userModalService.open(UserMgmtDialogComponent, params['login']);
            } else {
                this.modalRef = this.userModalService.open(UserMgmtDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
