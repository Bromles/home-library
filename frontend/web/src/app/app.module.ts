import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ThemeService} from "./theme.service";
import {StyleManagerService} from "./style-manager.service";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [ThemeService, StyleManagerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
