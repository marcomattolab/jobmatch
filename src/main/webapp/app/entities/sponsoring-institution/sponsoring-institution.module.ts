import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    SponsoringInstitutionComponent,
    SponsoringInstitutionProfileComponent,
    SponsoringInstitutionDetailComponent,
    SponsoringInstitutionUpdateComponent,
    SponsoringInstitutionDeletePopupComponent,
    SponsoringInstitutionDeleteDialogComponent,
    sponsoringInstitutionRoute,
    sponsoringInstitutionPopupRoute
} from './';

const ENTITY_STATES = [...sponsoringInstitutionRoute, ...sponsoringInstitutionPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SponsoringInstitutionComponent,
        SponsoringInstitutionProfileComponent,
        SponsoringInstitutionDetailComponent,
        SponsoringInstitutionUpdateComponent,
        SponsoringInstitutionDeleteDialogComponent,
        SponsoringInstitutionDeletePopupComponent
    ],
    entryComponents: [
        SponsoringInstitutionComponent,
        SponsoringInstitutionProfileComponent,
        SponsoringInstitutionUpdateComponent,
        SponsoringInstitutionDeleteDialogComponent,
        SponsoringInstitutionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchSponsoringInstitutionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
