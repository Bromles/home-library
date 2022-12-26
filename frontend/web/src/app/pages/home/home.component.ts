import {Component, OnDestroy} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {Subscription} from "rxjs";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnDestroy {
    private subscription: Subscription | undefined;

    constructor(private router: Router,
                private authService: AuthService) {
        this.subscription = this.authService.isLoggedIn$.subscribe((value) => {
            if (value) {
                this.router.navigate(['/books'])
            }
        })
    }

    ngOnDestroy(): void {
        this.subscription?.unsubscribe()
    }
}
