import {Route} from '@angular/router';

import {SidenavComponent} from './sidenav.component';
/* import { AuthService } from './shared';

 @Injectable()
 export class AuthorizeResolve implements Resolve<any> {

 constructor(private authService: AuthService) {}

 resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
 return this.authService.authorize();
 }
 } */

export const sideNavRoute: Route = {
    path: '',
    component: SidenavComponent,
    // resolve: {
    //   'authorize': AuthorizeResolve
    // },
    outlet: 'sidenav'
};
