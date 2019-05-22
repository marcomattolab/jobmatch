import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    CompanySkillComponent,
    CompanySkillDetailComponent,
    CompanySkillUpdateComponent,
    CompanySkillDeletePopupComponent,
    CompanySkillDeleteDialogComponent,
    companySkillRoute,
    companySkillPopupRoute
} from './';

const ENTITY_STATES = [...companySkillRoute, ...companySkillPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompanySkillComponent,
        CompanySkillDetailComponent,
        CompanySkillUpdateComponent,
        CompanySkillDeleteDialogComponent,
        CompanySkillDeletePopupComponent
    ],
    entryComponents: [
        CompanySkillComponent,
        CompanySkillUpdateComponent,
        CompanySkillDeleteDialogComponent,
        CompanySkillDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchCompanySkillModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
