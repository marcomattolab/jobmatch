import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { ImageProfilePipe } from './util/imageProfilePipe';
import { JobmatchSharedLibsModule, JobmatchSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { SecurePipe } from 'app/shared/auth/secure.pipe';
import { CompanyUpdateJobOfferDialogComponent } from 'app/entities/company/company-update-job-offer-dialog.component';
import { ConfirmDialogComponent } from './modal/confirm-dialog.component';
import { CompanyDeleteJobOfferDialogComponent } from 'app/entities/company/company-delete-job-offer-dialog.component';
import { CompanyChangeStateJobOfferDialogComponent } from 'app/entities/company/company-change-state-job-offer-dialog.component';
import { RouterModule } from '@angular/router';
import { CompanyUpdateSkillDialogComponent, CompanyUpdateAnagraficaDialogComponent, CompanyDeleteSkillDialogComponent } from 'app/entities/company';
import { SponsoringInstitutionUpdateAnagraficaDialogComponent } from 'app/entities/sponsoring-institution';
import { CandidateUpdateAnagraficaDialogComponent, CandidateUpdateJobExperiencesDialogComponent, CandidateDeleteJobExperienceDialogComponent,
         CandidateUpdateSkillDialogComponent, CandidateDeleteSkillDialogComponent, CandidateUpdateEducationDialogComponent, CandidateDeleteEducationDialogComponent,
         CandidateUpdateDocumentDialogComponent, CandidateDeleteDocumentDialogComponent } from 'app/entities/candidate';
import { AppliedJobUpdateStateDialogComponent } from 'app/entities/applied-job/applied-job-update-state-dialog.component';
import { JobOffersPromoteConfirmComponent } from 'app/entities/job-offer/job-offers-promote-confirm.component';

@NgModule( {
    imports: [JobmatchSharedLibsModule, JobmatchSharedCommonModule, RouterModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, SecurePipe, ImageProfilePipe, CompanyUpdateAnagraficaDialogComponent, CompanyDeleteSkillDialogComponent, SponsoringInstitutionUpdateAnagraficaDialogComponent,
        CompanyUpdateJobOfferDialogComponent, CompanyChangeStateJobOfferDialogComponent, ConfirmDialogComponent, CompanyDeleteJobOfferDialogComponent, CompanyUpdateSkillDialogComponent, CandidateUpdateAnagraficaDialogComponent,
        CandidateUpdateJobExperiencesDialogComponent, CandidateDeleteJobExperienceDialogComponent, CandidateUpdateSkillDialogComponent, CandidateDeleteSkillDialogComponent, CandidateUpdateEducationDialogComponent, CandidateDeleteEducationDialogComponent,
        CandidateUpdateDocumentDialogComponent, CandidateDeleteDocumentDialogComponent, AppliedJobUpdateStateDialogComponent, JobOffersPromoteConfirmComponent],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent, ConfirmDialogComponent, CompanyUpdateJobOfferDialogComponent, CompanyUpdateAnagraficaDialogComponent, SponsoringInstitutionUpdateAnagraficaDialogComponent,
        CompanyDeleteJobOfferDialogComponent, CompanyChangeStateJobOfferDialogComponent, CompanyUpdateSkillDialogComponent, CompanyDeleteSkillDialogComponent, CandidateUpdateAnagraficaDialogComponent,
        CandidateUpdateJobExperiencesDialogComponent, CandidateDeleteJobExperienceDialogComponent, CandidateUpdateSkillDialogComponent, CandidateDeleteSkillDialogComponent, CandidateUpdateEducationDialogComponent, CandidateDeleteEducationDialogComponent,
        CandidateUpdateDocumentDialogComponent, CandidateDeleteDocumentDialogComponent, AppliedJobUpdateStateDialogComponent, JobOffersPromoteConfirmComponent],
    exports: [JobmatchSharedCommonModule, JhiLoginModalComponent, CompanyUpdateJobOfferDialogComponent, CompanyUpdateAnagraficaDialogComponent, CompanyDeleteSkillDialogComponent, SponsoringInstitutionUpdateAnagraficaDialogComponent,
        CompanyDeleteJobOfferDialogComponent, CompanyChangeStateJobOfferDialogComponent, ConfirmDialogComponent, HasAnyAuthorityDirective, SecurePipe, ImageProfilePipe, CompanyUpdateSkillDialogComponent, CandidateUpdateAnagraficaDialogComponent,
        CandidateUpdateJobExperiencesDialogComponent, CandidateDeleteJobExperienceDialogComponent, CandidateUpdateSkillDialogComponent, CandidateDeleteSkillDialogComponent, CandidateUpdateEducationDialogComponent, CandidateDeleteEducationDialogComponent,
        CandidateUpdateDocumentDialogComponent, CandidateDeleteDocumentDialogComponent, AppliedJobUpdateStateDialogComponent, JobOffersPromoteConfirmComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
} )
export class JobmatchSharedModule {
    static forRoot() {
        return {
            ngModule: JobmatchSharedModule
        };
    }
}
