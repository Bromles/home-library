import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from "./services/auth.service";

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        let hasIdToken = this.authService.hasValidIdToken();
        let hasAccessToken = this.authService.hasValidAccessToken();

        if (hasAccessToken && hasIdToken) {
            let hasRole: boolean = false;
            const requiredRoles: string[] = route.data['roles'];

            const roles: string[] = this.authService.getRoles();

            if (roles.length === 0 || !requiredRoles) {
                hasRole = true;
            } else {
                for (const role of requiredRoles) {
                    if (roles.indexOf(role) > -1) {
                        hasRole = true;
                        break;
                    }
                }
            }

            if (!hasRole) {
                console.error("User does not have required role");
                this.router.navigate(['/']);
            }

            return hasRole;
        }

        if (!(hasIdToken && hasAccessToken)) {
            //console.error("User is not logged in");
            this.router.navigate(['/']);
        }

        //console.warn(`hasIdToken: ${hasIdToken}, hasAccessToken: ${hasAccessToken}`);
        return (hasIdToken && hasAccessToken);
    }

}
