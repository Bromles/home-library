import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReaderRoutingModule} from "./reader-routing.module";
import {ReaderComponent} from "./reader.component";

@NgModule({
    declarations: [
        ReaderComponent
    ],
    imports: [
        CommonModule,
        ReaderRoutingModule
    ],
    providers: [],
})export class ReaderModule {
}
