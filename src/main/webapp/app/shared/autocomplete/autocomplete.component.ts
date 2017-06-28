import {Component, ElementRef, OnInit} from '@angular/core';
import {DataHolderService} from '../../entities/receipt/data-holder.service';
import {ACElement} from './element.model';
import {ControlValueAccessor} from '@angular/forms';
@Component({
    selector: 'jhi-autocomplete',
    templateUrl: 'autocomplete.component.html'
})
export class AutocompleteComponent implements ControlValueAccessor, OnInit {

    public query: ACElement = {};
    public objects: ACElement[] = [];
    public filteredList = [];
    public elementRef;

    constructor(myElement: ElementRef,
                private dataHolderService: DataHolderService) {
        this.elementRef = myElement;
    }

    writeValue(obj: any): void {
    }

    ngOnInit() {
        if (this.dataHolderService._autocompleteObjects !== null && this.dataHolderService._autocompleteObjects.length > 0) {
            this.objects = this.dataHolderService._autocompleteObjects;
        }
    }

    filter() {
        if (this.query !== null && this.query.name !== '') {
            let elem: ACElement;
            for (const a of this.objects) {
                if (this.query.name === a.name) {
                    elem = a;
                }
            }
            this.filteredList = this.objects.filter(function(el: ACElement) {
                return el;
            }.bind(this.objects));
        } else {
            this.filteredList = [];
        }
    }

    select(item) {
        this.query = item;
        this.filteredList = [];
        this.dataHolderService._autocompleteSelected = item;
    }

    handleClick(event) {
        let clickedComponent = event.target;
        let inside = false;
        do {
            if (clickedComponent === this.elementRef.nativeElement) {
                inside = true;
            }
            clickedComponent = clickedComponent.parentNode;
        } while (clickedComponent);
        if (!inside) {
            this.filteredList = [];
        }
    }

    registerOnChange(fn: any): void {
        // this.onChangeCallback = fn;
    }

    registerOnTouched(fn: any): void {
        // this.onTouchedCallback = fn;
    }
}
