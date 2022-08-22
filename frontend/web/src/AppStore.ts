import {makeAutoObservable} from "mobx";

class Store {
    constructor() {
        makeAutoObservable(this);
        this._useDarkTheme = true;
    }

    private _useDarkTheme: boolean;

    public get useDarkTheme() {
        return this._useDarkTheme;
    }

    public set useDarkTheme(value: boolean) {
        this._useDarkTheme = value;
    }
}

export default new Store();