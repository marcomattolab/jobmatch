import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    JobOfferSkillComponent,
    JobOfferSkillDetailComponent,
    JobOfferSkillUpdateComponent,
    JobOfferSkillDeletePopupComponent,
    JobOfferSkillDeleteDialogComponent,
    jobOfferSkillRoute,
    jobOfferSkillPopupRoute
} from './';

const ENTITY_STATES = [...jobOfferSkillRoute, ...jobOfferSkillPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JobOfferSkillComponent,
        JobOfferSkillDetailComponent,
        JobOfferSkillUpdateComponent,
        JobOfferSkillDeleteDialogComponent,
        JobOfferSkillDeletePopupComponent
    ],
    entryComponents: [
        JobOfferSkillComponent,
        JobOfferSkillUpdateComponent,
        JobOfferSkillDeleteDialogComponent,
        JobOfferSkillDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchJobOfferSkillModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
