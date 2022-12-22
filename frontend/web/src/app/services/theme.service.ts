import {Injectable} from '@angular/core';
import {StyleManagerService} from "./style-manager.service";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ThemeService {
    isDarkTheme$: Observable<boolean>;
    private darkThemeSubject: BehaviorSubject<boolean>;

    constructor(private styleManager: StyleManagerService) {
        this.darkThemeSubject = new BehaviorSubject<boolean>(this.isInitialThemeDark());
        this.isDarkTheme$ = this.darkThemeSubject.asObservable();
        this.setInitialTheme()
    }

    toggleTheme() {
        this.setTheme(!this.darkThemeSubject.value);
    }

    private isInitialThemeDark() {
        const localStorageThemeDark = localStorage.getItem("isDarkTheme");

        if (localStorageThemeDark) {
            return localStorageThemeDark === "true";
        }

        return window.matchMedia("(prefers-color-scheme: dark)").matches;
    }

    private setInitialTheme() {
        this.setTheme(this.darkThemeSubject.value);
    }

    private setTheme(dark: boolean) {
        this.darkThemeSubject.next(dark);
        localStorage.setItem("isDarkTheme", dark.toString());

        this.styleManager.setStyle("theme", dark ? "assets/dark-theme.css" : "assets/light-theme.css");
    }
}
