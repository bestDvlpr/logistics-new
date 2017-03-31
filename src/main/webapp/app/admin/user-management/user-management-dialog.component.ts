import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, JhiLanguageService} from 'ng-jhipster';

import {UserModalService} from './user-modal.service';
import {JhiLanguageHelper, User, UserService} from '../../shared';
import {Shop} from '../../entities/shop/shop.model';
import {ShopService} from '../../entities/shop/shop.service';
import {Response} from '@angular/http';
import {DataHolderService} from '../../entities/receipt/data-holder.service';
import {ACElement} from '../../shared/autocomplete/element.model';

@Component({
    selector: 'jhi-user-mgmt-dialog',
    templateUrl: './user-management-dialog.component.html'
})
export class UserMgmtDialogComponent implements OnInit {

    user: User;
    languages: any[];
    authorities: any[];
    isSaving: Boolean;

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private jhiLanguageService: JhiLanguageService,
                private userService: UserService,
                private eventManager: EventManager,
                private dataHolderService: DataHolderService) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISPATCHER', 'ROLE_CASHIER', 'ROLE_MANAGER'];
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
        this.jhiLanguageService.setLocations(['user-management', 'shop']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.user.shopId = this.dataHolderService._autocompleteSelected.id;
        this.user.shopName = this.dataHolderService._autocompleteSelected.name;
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
}

@Component({
    selector: 'jhi-user-dialog',
    template: ''
})
export class UserDialogComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;
    shops: Shop[];

    constructor(private route: ActivatedRoute,
                private userModalService: UserModalService,
                private dataHolderService: DataHolderService,
                private shopService: ShopService) {
    }

    ngOnInit() {
        this.loadShops();
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

    private loadShops() {
        this.shopService.query().subscribe((res: Response) => {
            this.shops = res.json();
            this.setACObjects(this.shops);
        });
    }

    private setACObjects(shops: Shop[]) {
        this.dataHolderService.clearAll();
        if (shops !== null && shops.length > 0) {
            let acObjects: ACElement[] = [];
            for (let shop of shops) {
                let elem: ACElement = {};
                elem.name = shop.name;
                elem.id = shop.id;
                acObjects.push(elem);
            }
            this.dataHolderService._autocompleteObjects = acObjects;
            console.log(this.dataHolderService._autocompleteObjects);
        }
    }
}
