import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    AppliedJobComponent,
    AppliedJobDetailComponent,
    AppliedJobUpdateComponent,
    AppliedJobDeletePopupComponent,
    AppliedJobDeleteDialogComponent,
    appliedJobRoute,
    appliedJobPopupRoute
} from './';

const ENTITY_STATES = [...appliedJobRoute, ...appliedJobPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AppliedJobComponent,
        AppliedJobDetailComponent,
        AppliedJobUpdateComponent,
        AppliedJobDeleteDialogComponent,
        AppliedJobDeletePopupComponent
    ],
    entryComponents: [AppliedJobComponent, AppliedJobUpdateComponent, AppliedJobDeleteDialogComponent, AppliedJobDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchAppliedJobModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
