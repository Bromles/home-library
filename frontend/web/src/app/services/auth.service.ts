import {Injectable} from '@angular/core';
import {AuthConfig, OAuthService} from "angular-oauth2-oidc";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly config: AuthConfig = {
        issuer: 'http://localhost:8099/auth/realms/master',
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

    constructor(private oAuthService: OAuthService) {
        this.oAuthService.configure(this.config);
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
