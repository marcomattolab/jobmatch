import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    JobExperienceComponent,
    JobExperienceDetailComponent,
    JobExperienceUpdateComponent,
    JobExperienceDeletePopupComponent,
    JobExperienceDeleteDialogComponent,
    jobExperienceRoute,
    jobExperiencePopupRoute
} from './';

const ENTITY_STATES = [...jobExperienceRoute, ...jobExperiencePopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JobExperienceComponent,
        JobExperienceDetailComponent,
        JobExperienceUpdateComponent,
        JobExperienceDeleteDialogComponent,
        JobExperienceDeletePopupComponent
    ],
    entryComponents: [
        JobExperienceComponent,
        JobExperienceUpdateComponent,
        JobExperienceDeleteDialogComponent,
        JobExperienceDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchJobExperienceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
