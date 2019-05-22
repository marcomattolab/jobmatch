import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JobmatchSharedModule } from 'app/shared';
import {
    DirectApplicationComponent,
    DirectApplicationDetailComponent,
    DirectApplicationUpdateComponent,
    DirectApplicationDeletePopupComponent,
    DirectApplicationDeleteDialogComponent,
    directApplicationRoute,
    directApplicationPopupRoute
} from './';

const ENTITY_STATES = [...directApplicationRoute, ...directApplicationPopupRoute];

@NgModule({
    imports: [JobmatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DirectApplicationComponent,
        DirectApplicationDetailComponent,
        DirectApplicationUpdateComponent,
        DirectApplicationDeleteDialogComponent,
        DirectApplicationDeletePopupComponent
    ],
    entryComponents: [
        DirectApplicationComponent,
        DirectApplicationUpdateComponent,
        DirectApplicationDeleteDialogComponent,
        DirectApplicationDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchDirectApplicationModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
