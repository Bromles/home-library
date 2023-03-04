import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthButtonComponent } from './auth-button.component';
import {DateTimeProvider, OAuthLogger, OAuthService, UrlHelperService} from "angular-oauth2-oidc";
import {HttpClient, HttpHandler} from "@angular/common/http";
import {ThemeToggleComponent} from "../theme-toggle/theme-toggle.component";
import {AppModule} from "../../../app.module";
import {CategoryService} from "../../../services/category/category.service";
import {AuthService} from "../../../services/auth.service";

describe('AuthButtonComponent', () => {
  let component: AuthButtonComponent;
  let fixture: ComponentFixture<AuthButtonComponent>;
  let authService: AuthService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
      declarations: [ AuthButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthButtonComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService)
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('logout', () => {
    spyOn(authService, "logout").and.stub()
    component.logout()
  });

  it('login', () => {
    spyOn(authService, "login").and.stub()
    component.login()
  });
});
