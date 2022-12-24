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
import {AvatarModule} from "primeng/avatar";
import {MenuModule} from "primeng/menu";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { AuthButtonComponent } from './components/topbar/auth-button/auth-button.component';
import {AuthService} from "./services/auth.service";
import {HttpClientModule} from "@angular/common/http";
import {environment} from "../environments/environment";

@NgModule({
    declarations: [
        AppComponent,
        TopbarComponent,
        HomeComponent,
        ThemeToggleComponent,
        AuthButtonComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        ButtonModule,
        AvatarModule,
        MenuModule,
        HttpClientModule,
        OAuthModule.forRoot({
            resourceServer: {
                allowedUrls: [environment.backendUrl],
                sendAccessToken: true
            }
        }),
    ],
    providers: [
        ThemeService,
        StyleManagerService,
        {provide: OAuthStorage, useFactory: oAuthStorageFactory},
        AuthService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
