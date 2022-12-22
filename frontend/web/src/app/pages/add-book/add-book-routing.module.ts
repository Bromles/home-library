import {RouterModule, Routes} from "@angular/router";
import {AddBookComponent} from "./add-book.component";
import {NgModule} from "@angular/core";

const routes: Routes = [
    {
        path: '',
        component: AddBookComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AddBookRoutingModule {
}
