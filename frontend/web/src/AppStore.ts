import {makeAutoObservable} from "mobx";

export class AppStore {
    constructor(isDarkTheme: boolean) {
        makeAutoObservable(this);
        this._useDarkTheme = isDarkTheme;
    }

    private _useDarkTheme: boolean;

    public get useDarkTheme() {
        return this._useDarkTheme;
    }

    public set useDarkTheme(value: boolean) {
        this._useDarkTheme = value;
    }
}
