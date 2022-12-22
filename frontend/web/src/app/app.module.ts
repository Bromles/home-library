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

@NgModule({
    declarations: [
        AppComponent,
        TopbarComponent,
        HomeComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        OAuthModule.forRoot({
            resourceServer: {
                allowedUrls: ['http://localhost:8081/api/v1'],
                sendAccessToken: true
            }
        })
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
