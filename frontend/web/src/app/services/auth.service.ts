import {Injectable} from '@angular/core';
import {AuthConfig, OAuthService} from "angular-oauth2-oidc";
import {environment} from "../../environment/environment";
import {map, Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly config: AuthConfig = {
        issuer: environment.authIssuer,
        redirectUri: window.location.origin,
        logoutUrl: window.location.origin,
        clientId: 'frontend',
        requestAccessToken: true,
        oidc: true,
        responseType: 'code',
        scope: 'openid profile email offline_access roles',
        showDebugInformation: false,
        skipSubjectCheck: true,
    }

    isLoggedIn$: Observable<boolean>

    constructor(private oAuthService: OAuthService) {
        this.oAuthService.configure(this.config);

        this.isLoggedIn$ = this.oAuthService.events
            .pipe(
                map(e => this.isLoggedIn() || e.type === 'token_received')
            )
    }

    init() {
        this.oAuthService.loadDiscoveryDocumentAndTryLogin();
        this.oAuthService.setupAutomaticSilentRefresh();
    }

    login() {
        this.oAuthService.initLoginFlow();
    }

    logout() {
        this.oAuthService.logOut();
    }

    isLoggedIn() {
        return this.oAuthService.hasValidAccessToken();
    }

    getRoles(): string[] {
        const claims = this.oAuthService.getIdentityClaims();

        if (!claims) {
            return [];
        }

        const roles: string[] = claims['roles'];

        if (!roles) {
            throw Error('Roles not present');
        }

        return roles;
    }

    hasValidIdToken() {
        return this.oAuthService.hasValidAccessToken();
    }

    hasValidAccessToken() {
        return this.oAuthService.hasValidAccessToken();
    }

    getUsername(): string | null {
        return this.isLoggedIn() ? this.oAuthService.getIdentityClaims()['preferred_username'] : null;
    }

    getFirstLastName(): [string, string] | null {
        return this.isLoggedIn() ? [this.oAuthService.getIdentityClaims()['given_name'], this.oAuthService.getIdentityClaims()['family_name']] : null;
    }
}
