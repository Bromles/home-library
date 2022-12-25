import {Component, OnDestroy, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {AuthService} from "../../services/auth.service";
import {Observable, Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
    selector: 'app-topbar',
    templateUrl: './topbar.component.html',
    styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit, OnDestroy {

    private subscription: Subscription | undefined;

    items: MenuItem[] = [];

    isLoggedIn$: Observable<boolean>;

    constructor(private authService: AuthService,
                private httpClient: HttpClient) {
        this.isLoggedIn$ = this.authService.isLoggedIn$;
    }

    ngOnDestroy(): void {
        this.subscription?.unsubscribe()
    }

    addBook() {
        this.subscription = this.httpClient.get(environment.backendUrl + "book")
            .subscribe((value) => {
                console.log(value)
            })
    }

    ngOnInit() {
        this.items = [
            {label: 'Мои книги', routerLink: ['/my-books']},
            {label: 'Настройки', routerLink: ['/settings']},
        ];
    }
}
