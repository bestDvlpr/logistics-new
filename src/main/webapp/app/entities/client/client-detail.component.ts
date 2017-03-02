import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Client } from './client.model';
import { ClientService } from './client.service';

@Component({
    selector: 'jhi-client-detail',
    templateUrl: './client-detail.component.html'
})
export class ClientDetailComponent implements OnInit, OnDestroy {

    client: Client;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private clientService: ClientService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['client']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.clientService.find(id).subscribe(client => {
            this.client = client;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
