import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {BooksComponent} from "./books.component";

const routes: Routes = [
    {
        path: '',
        component: BooksComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BooksRoutingModule {
}
