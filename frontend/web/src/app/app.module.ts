import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ThemeService} from "./services/theme.service";
import {StyleManagerService} from "./services/style-manager.service";
import { TopbarComponent } from './components/topbar/topbar.component';
import { HomeComponent } from './pages/home/home.component';
import { AddBookComponent } from './pages/add-book/add-book.component';
import { BookComponent } from './pages/book/book.component';
import { LoginComponent } from './pages/login/login.component';
import { MyBooksComponent } from './pages/my-books/my-books.component';
import { ReaderComponent } from './pages/reader/reader.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { SettingsComponent } from './pages/settings/settings.component';

@NgModule({
    declarations: [
        AppComponent,
        TopbarComponent,
        HomeComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule
    ],
    providers: [ThemeService, StyleManagerService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
