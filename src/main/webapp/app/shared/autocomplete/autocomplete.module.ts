import {NgModule} from '@angular/core';
import {AutocompleteComponent} from './autocomplete.component';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
/**
 * Created by hasan on 3/31/17.
 */

@NgModule({
    imports: [FormsModule, CommonModule],
    declarations: [AutocompleteComponent],
    exports: [AutocompleteComponent],
    bootstrap: [AutocompleteComponent]
})
export class AutocompleteModule {

}
