import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    SkillTagComponent,
    SkillTagDetailComponent,
    SkillTagUpdateComponent,
    SkillTagDeletePopupComponent,
    SkillTagDeleteDialogComponent,
    skillTagRoute,
    skillTagPopupRoute
} from './';

const ENTITY_STATES = [...skillTagRoute, ...skillTagPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SkillTagComponent,
        SkillTagDetailComponent,
        SkillTagUpdateComponent,
        SkillTagDeleteDialogComponent,
        SkillTagDeletePopupComponent
    ],
    entryComponents: [SkillTagComponent, SkillTagUpdateComponent, SkillTagDeleteDialogComponent, SkillTagDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchSkillTagModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
