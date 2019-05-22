import { Routes } from '@angular/router';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';

import { RegisterComponent } from './register.component';

export const registerRoute: Routes = [{
    path: 'register',
    component: RegisterComponent,
    data: {
        authorities: [],
        pageTitle: 'register.title'
    }
},
{
    path: 'registerCandidate',
    component: RegisterComponent,
    data: {
        authorities: [],
        roleAccount: AuthoritiesConstants.ROLE_CANDIDATE,
        pageTitle: 'global.menu.account.registerCandidate'
    }
},
{
    path: 'registerCompany',
    component: RegisterComponent,
    data: {
        authorities: [],
        roleAccount: AuthoritiesConstants.ROLE_COMPANY,
        pageTitle: 'global.menu.account.registerCompany'
    }
},
{
    path: 'registerSponsoringInstitution',
    component: RegisterComponent,
    data: {
        authorities: [],
        roleAccount: AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION,
        pageTitle: 'global.menu.account.registerInstitution'
    }
}];
