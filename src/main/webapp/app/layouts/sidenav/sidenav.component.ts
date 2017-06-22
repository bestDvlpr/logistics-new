import {Component, OnInit} from "@angular/core";
import {LoginModalService, LoginService, Principal} from "../../shared";
import {Router} from "@angular/router";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'jhi-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.scss']
})
export class SidenavComponent implements OnInit {

    public isCollapsed: boolean = true;
    public isEntitiesCollapsed: boolean = true;
    public isAdministrationCollapsed: boolean = true;
    inProduction: boolean;
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(private principal: Principal,
                private loginService: LoginService,
                private router: Router,
                private loginModalService: LoginModalService) {
    }

    ngOnInit() {
    }


    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.loginService.logout();
        this.router.navigate(['']);
    }
}
