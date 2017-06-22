import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {Client} from "./client.model";
import {ClientPopupService} from "./client-popup.service";
import {ClientService} from "./client.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-client-delete-dialog',
    templateUrl: './client-delete-dialog.component.html'
})
export class ClientDeleteDialogComponent implements OnInit {
    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    client: Client;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private clientService: ClientService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clientListModification',
                content: 'Deleted an client'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-delete-popup',
    template: ''
})
export class ClientDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private clientPopupService: ClientPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.clientPopupService
                .open(ClientDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
