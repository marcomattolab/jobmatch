import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    AppliedJobIterationComponent,
    AppliedJobIterationDetailComponent,
    AppliedJobIterationUpdateComponent,
    AppliedJobIterationDeletePopupComponent,
    AppliedJobIterationDeleteDialogComponent,
    appliedJobIterationRoute,
    appliedJobIterationPopupRoute
} from './';

const ENTITY_STATES = [...appliedJobIterationRoute, ...appliedJobIterationPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AppliedJobIterationComponent,
        AppliedJobIterationDetailComponent,
        AppliedJobIterationUpdateComponent,
        AppliedJobIterationDeleteDialogComponent,
        AppliedJobIterationDeletePopupComponent
    ],
    entryComponents: [
        AppliedJobIterationComponent,
        AppliedJobIterationUpdateComponent,
        AppliedJobIterationDeleteDialogComponent,
        AppliedJobIterationDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchAppliedJobIterationModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
