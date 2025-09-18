import { Routes } from '@angular/router';

export const routes: Routes = [
    {path: '', redirectTo: '/index', pathMatch: "full"},
    {
        path: 'index',
        loadComponent: () => import('./index/index-page/index').then(m => m.IndexPage)
    },
    {
        path: 'login',
        loadComponent: () => import('./login/login-page/login').then(m =>m.LoginPage)
    },
    {
        path: 'gallery',
        loadComponent: () => import('./gallery/gallery-page/gallery').then(m =>m.GalleryPage)
    }
];
