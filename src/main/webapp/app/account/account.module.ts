import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {LogisticsSharedModule} from "../shared";

import {
    accountState,
    ActivateComponent,
    ActivateService,
    PasswordComponent,
    PasswordResetFinishComponent,
    PasswordResetFinishService,
    PasswordResetInitComponent,
    PasswordResetInitService,
    PasswordService,
    PasswordStrengthBarComponent,
    Register,
    RegisterComponent,
    SettingsComponent
} from "./";

@NgModule({
    imports: [
        LogisticsSharedModule,
        RouterModule.forRoot(accountState, { useHash: true })
    ],
    declarations: [
        ActivateComponent,
        RegisterComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SettingsComponent
    ],
    providers: [
        Register,
        ActivateService,
        PasswordService,
        PasswordResetInitService,
        PasswordResetFinishService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsAccountModule {}
