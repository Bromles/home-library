import {Component, OnDestroy} from '@angular/core';
import {ThemeService} from "../../../services/theme.service";
import {Subscription} from "rxjs";

@Component({
    selector: 'app-theme-toggle',
    templateUrl: './theme-toggle.component.html',
    styleUrls: ['./theme-toggle.component.scss']
})
export class ThemeToggleComponent implements OnDestroy {
    private iconSuffix: string = 'sun';
    private subscription: Subscription;

    constructor(private themeService: ThemeService) {
        this.subscription = this.themeService.isDarkTheme$.subscribe(isDarkTheme => {
            this.iconSuffix = isDarkTheme ? 'moon' : 'sun';
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    toggleTheme() {
        this.themeService.toggleTheme()
    }

    currentIcon() {
        return 'pi pi-' + this.iconSuffix;
    }
}
