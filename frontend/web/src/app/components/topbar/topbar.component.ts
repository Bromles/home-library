import {Component, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {AuthService} from "../../services/auth.service";
import {Observable} from "rxjs";

@Component({
    selector: 'app-topbar',
    templateUrl: './topbar.component.html',
    styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {

    items: MenuItem[] = [];

    isLoggedIn$: Observable<boolean>;

    constructor(private authService: AuthService) {
        this.isLoggedIn$ = this.authService.isLoggedIn$;
    }

    ngOnInit() {
        this.items = [
            {label: 'Мои книги', routerLink: ['/my-books']},
            {label: 'Настройки', routerLink: ['/settings']},
        ];
    }
}
