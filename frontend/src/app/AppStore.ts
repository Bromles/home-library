import {makeAutoObservable} from "mobx";

class Store {
    private _useDarkTheme: boolean;

    constructor() {
        makeAutoObservable(this);
        this._useDarkTheme = true;
    }

    public get useDarkTheme() {
        return this._useDarkTheme;
    }

    public set useDarkTheme(value: boolean) {
        this._useDarkTheme = value;
    }
}

export default new Store();