import {Component, Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {LoyaltyCard} from "./loyalty-card.model";
import {LoyaltyCardService} from "./loyalty-card.service";
@Injectable()
export class LoyaltyCardPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private loyaltyCardService: LoyaltyCardService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.loyaltyCardService.find(id).subscribe(loyaltyCard => {
                this.loyaltyCardModalRef(component, loyaltyCard);
            });
        } else {
            return this.loyaltyCardModalRef(component, new LoyaltyCard());
        }
    }

    loyaltyCardModalRef(component: Component, loyaltyCard: LoyaltyCard): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.loyaltyCard = loyaltyCard;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
