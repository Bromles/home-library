import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ThemeService} from "./services/theme.service";
import {StyleManagerService} from "./services/style-manager.service";
import {TopbarComponent} from './components/topbar/topbar.component';
import {HomeComponent} from './pages/home/home.component';
import {OAuthModule, OAuthStorage} from "angular-oauth2-oidc";
import {oAuthStorageFactory} from "./utils/oAuthStorageFactory";
import { ThemeToggleComponent } from './components/topbar/theme-toggle/theme-toggle.component';
import {ButtonModule} from "primeng/button";

@NgModule({
    declarations: [
        AppComponent,
        TopbarComponent,
        HomeComponent,
        ThemeToggleComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        OAuthModule.forRoot({
            resourceServer: {
                allowedUrls: ['http://localhost:8081/api/v1'],
                sendAccessToken: true
            }
        }),
        ButtonModule
    ],
    providers: [
        ThemeService,
        StyleManagerService,
        {provide: OAuthStorage, useFactory: oAuthStorageFactory}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
