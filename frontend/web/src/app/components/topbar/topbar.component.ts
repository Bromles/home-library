import {Component, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";

@Component({
    selector: 'app-topbar',
    templateUrl: './topbar.component.html',
    styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {
    items: MenuItem[] = [];

    ngOnInit() {
        this.items = [
            {label: 'Мои книги', routerLink: ['/my-books']},
            {label: 'Настройки', routerLink: ['/settings']},
        ];
    }
}
