import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {BookRoutingModule} from "./book-routing.module";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        BookRoutingModule,
        ReactiveFormsModule,
    ],
    providers: [],
})export class BookModule {
}
