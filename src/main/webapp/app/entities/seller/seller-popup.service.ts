import {Component, Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {Seller} from "./seller.model";
import {SellerService} from "./seller.service";
@Injectable()
export class SellerPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private sellerService: SellerService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.sellerService.find(id).subscribe(seller => {
                this.sellerModalRef(component, seller);
            });
        } else {
            return this.sellerModalRef(component, new Seller());
        }
    }

    sellerModalRef(component: Component, seller: Seller): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.seller = seller;
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
