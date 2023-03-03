import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import {AppModule} from "../app.module";
import {OAuthService} from "angular-oauth2-oidc";

describe('AuthService', () => {
  let service: AuthService;
  let oAuthService: OAuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AppModule]
    });
    service = TestBed.inject(AuthService);
    oAuthService = TestBed.inject(OAuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('login', () => {
    spyOn(oAuthService, "initLoginFlow")
      .and.stub()
    service.login()
  });

  it('logout', () => {
    spyOn(oAuthService, "logOut")
      .and.stub()
    service.logout()
  });

  it('getRoles', () => {
    spyOn(oAuthService, "getIdentityClaims")
      .and.stub()
    service.getRoles()
  });


});
