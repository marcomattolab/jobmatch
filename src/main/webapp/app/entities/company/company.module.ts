import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageHelper } from 'app/core';
import { JobmatchSharedModule } from 'app/shared';
import { JhiLanguageService } from 'ng-jhipster';
import { CompanyComponent, CompanyDeleteDialogComponent, CompanyDeletePopupComponent,
    CompanyDetailComponent, companyPopupRoute, CompanyProfileComponent, companyRoute,
    CompanyProfileListComponent, CompanyUpdateComponent, CompanyUpdateJobOfferComponent } from './';

const ENTITY_STATES = [...companyRoute, ...companyPopupRoute];

@NgModule( {
    imports: [JobmatchSharedModule, RouterModule.forChild( ENTITY_STATES )],
    declarations: [
        CompanyComponent,
        CompanyProfileListComponent,
        CompanyProfileComponent,
        CompanyUpdateJobOfferComponent,
        CompanyDetailComponent,
        CompanyUpdateComponent,
        CompanyDeleteDialogComponent,
        CompanyDeletePopupComponent
    ],
    entryComponents: [CompanyComponent, CompanyProfileListComponent,
        CompanyProfileComponent, CompanyUpdateComponent,
        CompanyUpdateJobOfferComponent, CompanyDeleteDialogComponent,
        CompanyDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
} )
export class JobmatchCompanyModule {
    constructor( private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper ) {
        this.languageHelper.language.subscribe(( languageKey: string ) => {
            if ( languageKey !== undefined ) {
                this.languageService.changeLanguage( languageKey );
            }
        } );
    }
}
