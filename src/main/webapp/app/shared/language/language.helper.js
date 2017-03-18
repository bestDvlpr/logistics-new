"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var language_constants_1 = require("./language.constants");
var JhiLanguageHelper = (function () {
    function JhiLanguageHelper(translateService, titleService) {
        this.translateService = translateService;
        this.titleService = titleService;
        this.init();
    }
    JhiLanguageHelper.prototype.getAll = function () {
        return Promise.resolve(language_constants_1.LANGUAGES);
    };
    /**
     * Update the window title using params in the following
     * order:
     * 1. titleKey parameter
     * 2. $state.$current.data.pageTitle (current state page title)
     * 3. 'global.title'
     */
    JhiLanguageHelper.prototype.updateTitle = function (titleKey) {
        var _this = this;
        if (!titleKey && this.titleService.getTitle()) {
            titleKey = this.titleService.getTitle();
        }
        this.translateService.get(titleKey || 'global.title').subscribe(function (title) {
            _this.titleService.setTitle(title);
        });
    };
    JhiLanguageHelper.prototype.init = function () {
    };
    return JhiLanguageHelper;
}());
JhiLanguageHelper = __decorate([
    core_1.Injectable()
], JhiLanguageHelper);
exports.JhiLanguageHelper = JhiLanguageHelper;
