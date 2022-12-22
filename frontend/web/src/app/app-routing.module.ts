import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {AuthGuard} from "./auth.guard";

const routes: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'add-book',
        loadChildren: () => import('./pages/add-book/add-book.module').then(m => m.AddBookModule)
    },
    {
        path: 'book',
        loadChildren: () => import('./pages/book/book.module').then(m => m.BookModule)
    },
    {
        path: 'my-books',
        loadChildren: () => import('./pages/my-books/my-books.module').then(m => m.MyBooksModule)
    },
    {
        path: 'reader',
        loadChildren: () => import('./pages/reader/reader.module').then(m => m.ReaderModule)
    },
    {
        path: 'settings',
        loadChildren: () => import('./pages/settings/settings.module').then(m => m.SettingsModule),
        canActivate: [AuthGuard]
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
