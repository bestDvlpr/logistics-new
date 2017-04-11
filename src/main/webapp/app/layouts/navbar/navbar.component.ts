import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiLanguageService} from 'ng-jhipster';

import {ProfileService} from '../profiles/profile.service'; // FIXME barrel doesnt work here
import {JhiLanguageHelper, Principal, LoginModalService, LoginService} from '../../shared';

import {VERSION, DEBUG_INFO_ENABLED} from '../../app.constants';
import {ReceiptService} from '../../entities/receipt/receipt.service';
import {Observable} from 'rxjs';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ]
})
export class NavbarComponent implements OnInit {

    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    newOrderCount: number = 0;
    appliedOrderCount: number = 0;

    constructor(private loginService: LoginService,
                private languageHelper: JhiLanguageHelper,
                private languageService: JhiLanguageService,
                private principal: Principal,
                private loginModalService: LoginModalService,
                private profileService: ProfileService,
                private receiptService: ReceiptService,
                private router: Router) {
        this.version = DEBUG_INFO_ENABLED ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        this.languageService.setLocations(['home', 'receipt']);
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().subscribe(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });

        this.getNewReceiptCount();
        this.getAppliedReceiptsCount();
    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

    private getNewReceiptCount() {
        let pollData = this.receiptService.countNewApplications();
        if (this.principal.isAuthenticated()) {
            pollData.expand(() => Observable.interval(60000).flatMap(() => pollData)).subscribe(count => {
                this.newOrderCount = count;
            });
        }
    }

    private getAppliedReceiptsCount() {
        let pollData = this.receiptService.countAppliedApplications();
        if (this.principal.isAuthenticated()) {
            pollData.expand(() => Observable.interval(60000).flatMap(() => pollData)).subscribe(count => {
                this.appliedOrderCount = count;
            });
        }
    }
}
