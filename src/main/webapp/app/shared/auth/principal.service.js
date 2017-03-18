"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var Subject_1 = require("rxjs/Subject");
var Principal = (function () {
    function Principal(account) {
        this.account = account;
        this.authenticated = false;
        this.authenticationState = new Subject_1.Subject();
    }
    Principal.prototype.authenticate = function (_identity) {
        this._identity = _identity;
        this.authenticated = _identity !== null;
    };
    Principal.prototype.hasAnyAuthority = function (authorities) {
        if (!this.authenticated || !this._identity || !this._identity.authorities) {
            return false;
        }
        for (var i = 0; i < authorities.length; i++) {
            if (this._identity.authorities.indexOf(authorities[i]) !== -1) {
                return true;
            }
        }
        return false;
    };
    Principal.prototype.hasAuthority = function (authority) {
        if (!this.authenticated) {
            return Promise.resolve(false);
        }
        return this.identity().then(function (id) {
            return id.authorities && id.authorities.indexOf(authority) !== -1;
        }, function () {
            return false;
        });
    };
    Principal.prototype.identity = function (force) {
        var _this = this;
        if (force === true) {
            this._identity = undefined;
        }
        // check and see if we have retrieved the _identity data from the server.
        // if we have, reuse it by immediately resolving
        if (this._identity) {
            return Promise.resolve(this._identity);
        }
        // retrieve the _identity data from the server, update the _identity object, and then resolve.
        return this.account.get().toPromise().then(function (account) {
            if (account) {
                _this._identity = account;
                _this.authenticated = true;
            }
            else {
                _this._identity = null;
                _this.authenticated = false;
            }
            _this.authenticationState.next(_this._identity);
            return _this._identity;
        }).catch(function (err) {
            _this._identity = null;
            _this.authenticated = false;
            _this.authenticationState.next(_this._identity);
            return null;
        });
    };
    Principal.prototype.isAuthenticated = function () {
        return this.authenticated;
    };
    Principal.prototype.isIdentityResolved = function () {
        return this._identity !== undefined;
    };
    Principal.prototype.getAuthenticationState = function () {
        return this.authenticationState.asObservable();
    };
    Principal.prototype.getImageUrl = function () {
        return this.isIdentityResolved() ? this._identity.imageUrl : null;
    };
    return Principal;
}());
Principal = __decorate([
    core_1.Injectable()
], Principal);
exports.Principal = Principal;
