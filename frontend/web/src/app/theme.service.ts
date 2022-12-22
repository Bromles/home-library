import { Injectable } from '@angular/core';
import {StyleManagerService} from "./style-manager.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  isDarkTheme$: boolean;

  constructor(private styleManager: StyleManagerService) {
    this.setInitialTheme();
  }

  setInitialTheme() {
    this.isDarkTheme$ = true;
  }

  toggleTheme() {
    this.styleManager.setStyle(
        "theme",
        "assets/theme-dark.css"
    )
  }
}
