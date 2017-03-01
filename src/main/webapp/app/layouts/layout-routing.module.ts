import { NgModule } from '@angular/core';
import { RouterModule, Routes, Resolve } from '@angular/router';

import { homeRoute } from '../home';
import { navbarRoute } from '../app.route';
import { errorRoute } from './';
import {sideNavRoute} from './sidenav/sideNav.route';

let LAYOUT_ROUTES = [
    homeRoute,
    navbarRoute,
    sideNavRoute,
    ...errorRoute
];

@NgModule({
  imports: [
    RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true })
  ],
  exports: [
    RouterModule
  ]
})
export class LayoutRoutingModule {}
