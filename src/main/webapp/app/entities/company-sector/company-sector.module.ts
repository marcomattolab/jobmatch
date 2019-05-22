import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    CompanySectorComponent,
    CompanySectorDetailComponent,
    CompanySectorUpdateComponent,
    CompanySectorDeletePopupComponent,
    CompanySectorDeleteDialogComponent,
    companySectorRoute,
    companySectorPopupRoute
} from './';

const ENTITY_STATES = [...companySectorRoute, ...companySectorPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompanySectorComponent,
        CompanySectorDetailComponent,
        CompanySectorUpdateComponent,
        CompanySectorDeleteDialogComponent,
        CompanySectorDeletePopupComponent
    ],
    entryComponents: [
        CompanySectorComponent,
        CompanySectorUpdateComponent,
        CompanySectorDeleteDialogComponent,
        CompanySectorDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchCompanySectorModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
