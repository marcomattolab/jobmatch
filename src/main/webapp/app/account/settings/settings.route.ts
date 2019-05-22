import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { SettingsComponent } from './settings.component';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';

export const settingsRoute: Route = {
    path: 'settings',
    component: SettingsComponent,
    data: {
        authorities: [
                      AuthoritiesConstants.ROLE_ADMIN,
                      AuthoritiesConstants.ROLE_CANDIDATE,
                      AuthoritiesConstants.ROLE_COMPANY,
                      AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION
                      ],
        pageTitle: 'global.menu.account.settings'
    },
    canActivate: [UserRouteAccessService]
};
