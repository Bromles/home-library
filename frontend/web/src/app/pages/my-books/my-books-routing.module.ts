import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {MyBooksComponent} from "./my-books.component";

const routes: Routes = [
    {
        path: '',
        component: MyBooksComponent,
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MyBooksRoutingModule {
}
