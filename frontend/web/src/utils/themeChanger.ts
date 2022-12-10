import {AppStore} from "../AppStore";

export const themeChanger = (store: AppStore) => {
    let link: HTMLElement | null = document.getElementById('app-theme');

    if (link === null) {
        const tag = document.createElement("link");

        tag.setAttribute("rel", "stylesheet");
        tag.setAttribute("type", "text/css");
        tag.setAttribute('id', 'app-theme');

        document.head.append(tag)

        link = document.getElementById('app-theme');
    }

    link?.setAttribute('href', store.useDarkTheme ? '/theme-dark.css' : '/theme-light.css')
    localStorage.setItem('useDarkTheme', store.useDarkTheme.toString());
}
