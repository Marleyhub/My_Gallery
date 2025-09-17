// src/main.ts

import { bootstrapApplication } from '@angular/platform-browser';
import { importProvidersFrom } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app/app';
import { ToolBar } from './app/tool-bar/tool-bar';
import { routes } from './app/app.routes';  // define your routes in app.routes.ts

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(RouterModule.forRoot(routes))
  ]
})
.catch(err => console.error(err));

bootstrapApplication(ToolBar, {
  providers: [
    importProvidersFrom(RouterModule.forRoot(routes))
  ]
})
.catch(err => console.error(err));
