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
        loadChildren: () => import('./pages/add-book/add-book.module').then(m => m.AddBookModule),
        canActivate: [AuthGuard],
        data: {
            roles: ['user']
        }
    },
    {
        path: 'books/:id',
        loadChildren: () => import('./pages/book/book.module').then(m => m.BookModule),
        canActivate: [AuthGuard],
        data: {
            roles: ['user']
        }
    },
    {
        path: 'books',
        loadChildren: () => import('./pages/books/books.module').then(m => m.BooksModule),
        canActivate: [AuthGuard],
        data: {
            roles: ['user']
        }
    },
    {
        path: 'my-books',
        loadChildren: () => import('./pages/my-books/my-books.module').then(m => m.MyBooksModule),
        canActivate: [AuthGuard],
        data: {
            roles: ['user']
        }
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
