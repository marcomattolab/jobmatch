import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageHelper } from 'app/core';
import { JobmatchSharedModule } from 'app/shared';
import { JhiLanguageService } from 'ng-jhipster';
import { JobOfferComponent, JobOfferDeleteDialogComponent, JobOfferDeletePopupComponent,
    JobOfferDetailComponent, jobOfferPopupRoute, jobOfferRoute, JobOfferUpdateComponent,
    JobsOfferListComponent, JobsOfferPromoteComponent } from './';

const ENTITY_STATES = [...jobOfferRoute, ...jobOfferPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JobOfferComponent,
        JobOfferDetailComponent,
        JobOfferUpdateComponent,
        JobOfferDeleteDialogComponent,
        JobOfferDeletePopupComponent,
        JobsOfferListComponent,
        JobsOfferPromoteComponent
    ],
    entryComponents: [JobOfferComponent, JobOfferUpdateComponent, JobOfferDeleteDialogComponent, JobOfferDeletePopupComponent, JobsOfferListComponent, JobsOfferPromoteComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchJobOfferModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
