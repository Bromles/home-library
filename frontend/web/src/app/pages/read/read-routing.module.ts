import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ReadComponent} from "./read.component";

const routes: Routes = [
    {
        path: '',
        component: ReadComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ReadRoutingModule {
}
