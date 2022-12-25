import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {BookRoutingModule} from "./book-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import {BookComponent} from "./book.component";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";

@NgModule({
    declarations: [
      BookComponent,
    ],
    imports: [
        CommonModule,
        BookRoutingModule,
        ReactiveFormsModule,
        InputTextModule,
        ButtonModule,
    ],
    providers: [],
})export class BookModule {
}
