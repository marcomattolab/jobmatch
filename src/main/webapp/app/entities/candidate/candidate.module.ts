import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageHelper } from 'app/core';
import { JobmatchSharedModule } from 'app/shared';
import { JhiLanguageService } from 'ng-jhipster';
import { CandidateComponent, CandidateProfileListComponent, CandidateDeleteDialogComponent,
    CandidateDeletePopupComponent, CandidateDetailComponent, candidatePopupRoute,
    CandidateProfileComponent, candidateRoute, CandidateUpdateComponent } from './';

const ENTITY_STATES = [...candidateRoute, ...candidatePopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CandidateComponent,
        CandidateProfileListComponent,
        CandidateDetailComponent,
        CandidateProfileComponent,
        CandidateUpdateComponent,
        CandidateDeleteDialogComponent,
        CandidateDeletePopupComponent,
    ],
    entryComponents: [
        CandidateComponent,
        CandidateProfileListComponent,
        CandidateProfileComponent,
        CandidateUpdateComponent,
        CandidateDeleteDialogComponent,
        CandidateDeletePopupComponent,
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchCandidateModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
