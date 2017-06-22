/**
 * Created by hasan on 2/22/17.
 */
import {Directive, HostBinding, Input} from "@angular/core";


@Directive({selector: '[jhiCollapse]'})
export class CollapseDirective {
    // style
    @HostBinding('style.height')
    private height: string;
    // shown
    @HostBinding('class.in')
    @HostBinding('attr.aria-expanded')
    private isExpanded: boolean = true;
    // hidden
    @HostBinding('attr.aria-hidden')
    private isJhiCollapsed: boolean = false;
    // stale state
    @HostBinding('class.collapse')
    private isJhiCollapse: boolean = true;
    // animation state
    @HostBinding('class.collapsing')
    private isJhiCollapsing: boolean = false;

    @Input()
    private set jhiCollapse(value: boolean) {
        this.isExpanded = value;
        this.toggle();
    }

    private get jhiCollapse(): boolean {
        return this.isExpanded;
    }

    constructor() {
    }

    toggle() {
        if (this.isExpanded) {
            this.hide();
        } else {
            this.show();
        }
    }

    hide() {
        this.isJhiCollapse = false;
        this.isJhiCollapsing = true;

        this.isExpanded = false;
        this.isJhiCollapsed = true;
        setTimeout(() => {
            this.height = '0';
            this.isJhiCollapse = true;
            this.isJhiCollapsing = false;
        }, 4);
    }

    show() {
        this.isJhiCollapse = false;
        this.isJhiCollapsing = true;

        this.isExpanded = true;
        this.isJhiCollapsed = false;
        setTimeout(() => {
            this.height = 'auto';

            this.isJhiCollapse = true;
            this.isJhiCollapsing = false;
        }, 4);
    }
}
