import {Component, OnDestroy} from '@angular/core';
import {AuthService} from "./services/auth.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
    constructor(private authService: AuthService) {
        this.authService.init();
    }
}
