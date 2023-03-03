import {TestBed} from '@angular/core/testing';

import {AuthGuard} from './auth.guard';
import {AppModule} from "./app.module";
import {ActivatedRouteSnapshot, Data, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "./services/auth.service";

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let route: ActivatedRouteSnapshot;
  let state: RouterStateSnapshot;
  let authService: AuthService;

  const fakeRouterStateSnapshot = {
    url: {},
  } as RouterStateSnapshot;
  const fakeActivatedRouteSnapshot = {
    data: {
      subscribe: (fn: (value: Data) => void) => fn({
        roles: ["USER"],
      })
    },
  } as unknown as ActivatedRouteSnapshot;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AppModule],
      providers: [
        {provide: ActivatedRouteSnapshot, useValue: fakeActivatedRouteSnapshot},
        {provide: RouterStateSnapshot, useValue: fakeRouterStateSnapshot},
      ]
    });
    guard = TestBed.inject(AuthGuard);
    authService = TestBed.inject(AuthService)
    route = TestBed.inject(ActivatedRouteSnapshot)
    state = TestBed.inject(RouterStateSnapshot)
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('User is not logged in', () => {
    guard.canActivate(route, state)
  });

  it('should be created', () => {
    spyOn(authService, "hasValidIdToken")
      .and.returnValue(true)
    spyOn(authService, "hasValidAccessToken")
      .and.returnValue(true)
    guard.canActivate(route, state)
  });
});
