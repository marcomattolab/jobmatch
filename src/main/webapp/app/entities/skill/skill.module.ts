import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    SkillComponent,
    SkillDetailComponent,
    SkillUpdateComponent,
    SkillDeletePopupComponent,
    SkillDeleteDialogComponent,
    skillRoute,
    skillPopupRoute
} from './';

const ENTITY_STATES = [...skillRoute, ...skillPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SkillComponent, SkillDetailComponent, SkillUpdateComponent, SkillDeleteDialogComponent, SkillDeletePopupComponent],
    entryComponents: [SkillComponent, SkillUpdateComponent, SkillDeleteDialogComponent, SkillDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchSkillModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
