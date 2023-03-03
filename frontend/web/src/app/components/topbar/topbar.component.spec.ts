import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopbarComponent } from './topbar.component';
import {DateTimeProvider, OAuthLogger, OAuthService, UrlHelperService} from "angular-oauth2-oidc";
import {HttpClient, HttpHandler} from "@angular/common/http";
import {ThemeToggleComponent} from "./theme-toggle/theme-toggle.component";
import {AppModule} from "../../app.module";

describe('TopbarComponent', () => {
  let component: TopbarComponent;
  let fixture: ComponentFixture<TopbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopbarComponent ],
      imports: [AppModule],
      providers: [ OAuthService, HttpClient, HttpHandler, UrlHelperService, OAuthLogger, DateTimeProvider, ThemeToggleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
