import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    EducationComponent,
    EducationDetailComponent,
    EducationUpdateComponent,
    EducationDeletePopupComponent,
    EducationDeleteDialogComponent,
    educationRoute,
    educationPopupRoute
} from './';

const ENTITY_STATES = [...educationRoute, ...educationPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EducationComponent,
        EducationDetailComponent,
        EducationUpdateComponent,
        EducationDeleteDialogComponent,
        EducationDeletePopupComponent
    ],
    entryComponents: [EducationComponent, EducationUpdateComponent, EducationDeleteDialogComponent, EducationDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchEducationModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
