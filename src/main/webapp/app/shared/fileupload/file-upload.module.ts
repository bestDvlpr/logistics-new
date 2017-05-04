import {FileUploadComponent} from "./file-upload.component";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {NgModule} from "@angular/core";
/**
 * Created by hasan on 5/3/17.
 */
@NgModule({
    imports: [FormsModule, CommonModule],
    declarations: [FileUploadComponent],
    exports: [FileUploadComponent],
    bootstrap: [FileUploadComponent]
})
export class FileUploadModule {

}
