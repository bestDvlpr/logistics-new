"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var HasAuthorityDirective = (function () {
    function HasAuthorityDirective(principal, el, renderer) {
        this.principal = principal;
        this.el = el;
        this.renderer = renderer;
    }
    HasAuthorityDirective.prototype.ngOnInit = function () {
        var _this = this;
        this.authority = this.jhiHasAuthority.replace(/\s+/g, '');
        if (this.authority.length > 0) {
            this.setVisibilityAsync();
        }
        this.principal.getAuthenticationState().subscribe(function (identity) { return _this.setVisibilitySync(); });
    };
    HasAuthorityDirective.prototype.setVisibilitySync = function () {
        if (this.principal.hasAnyAuthority([this.authority])) {
            this.setVisible();
        }
        else {
            this.setHidden();
        }
    };
    HasAuthorityDirective.prototype.setVisible = function () {
        this.renderer.setElementClass(this.el.nativeElement, 'hidden-xs-up', false);
    };
    HasAuthorityDirective.prototype.setHidden = function () {
        this.renderer.setElementClass(this.el.nativeElement, 'hidden-xs-up', true);
    };
    HasAuthorityDirective.prototype.setVisibilityAsync = function () {
        var _this = this;
        this.principal.hasAuthority(this.authority).then(function (result) {
            if (result) {
                _this.setVisible();
            }
            else {
                _this.setHidden();
            }
        });
    };
    return HasAuthorityDirective;
}());
__decorate([
    core_1.Input()
], HasAuthorityDirective.prototype, "jhiHasAuthority", void 0);
HasAuthorityDirective = __decorate([
    core_1.Directive({
        selector: '[jhiHasAuthority]'
    })
], HasAuthorityDirective);
exports.HasAuthorityDirective = HasAuthorityDirective;
