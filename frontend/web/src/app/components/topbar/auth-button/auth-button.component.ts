import {Component, OnDestroy} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {Observable, Subscription} from "rxjs";

@Component({
    selector: 'app-auth-button',
    templateUrl: './auth-button.component.html',
    styleUrls: ['./auth-button.component.scss']
})
export class AuthButtonComponent {
    isLoggedIn$: Observable<boolean>;


    constructor(private authService: AuthService) {
        this.isLoggedIn$ = this.authService.isLoggedIn$;
    }

    login() {
        this.authService.login()
    }

    logout() {
        this.authService.logout()
    }
}
