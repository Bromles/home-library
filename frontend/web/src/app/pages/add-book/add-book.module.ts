import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {AddBookRoutingModule} from "./add-book-routing.module";
import {AddBookComponent} from "./add-book.component";
import {ReactiveFormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import {FileUploadModule} from "primeng/fileupload";
import {DropdownModule} from "primeng/dropdown";
import {InputMaskModule} from "primeng/inputmask";

@NgModule({
    declarations: [
        AddBookComponent,
    ],
  imports: [
    CommonModule,
    AddBookRoutingModule,
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    FileUploadModule,
    DropdownModule,
    InputMaskModule,
  ],
    providers: [],
})export class AddBookModule {
}
