import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { PasswordComponent } from './password.component';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';

export const passwordRoute: Route = {
    path: 'password',
    component: PasswordComponent,
    data: {
        authorities: [
                      AuthoritiesConstants.ROLE_ADMIN,
                      AuthoritiesConstants.ROLE_CANDIDATE,
                      AuthoritiesConstants.ROLE_COMPANY,
                      AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION
                      ],
        pageTitle: 'global.menu.account.password'
    },
    canActivate: [UserRouteAccessService]
};
