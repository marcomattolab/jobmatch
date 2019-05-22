import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'candidate',
                loadChildren: './candidate/candidate.module#JobmatchCandidateModule'
            },
            {
                path: 'job-experience',
                loadChildren: './job-experience/job-experience.module#JobmatchJobExperienceModule'
            },
            {
                path: 'education',
                loadChildren: './education/education.module#JobmatchEducationModule'
            },
            {
                path: 'skill',
                loadChildren: './skill/skill.module#JobmatchSkillModule'
            },
            {
                path: 'skill-tag',
                loadChildren: './skill-tag/skill-tag.module#JobmatchSkillTagModule'
            },
            {
                path: 'company',
                loadChildren: './company/company.module#JobmatchCompanyModule'
            },
            {
                path: 'sponsoring-institution',
                loadChildren: './sponsoring-institution/sponsoring-institution.module#JobmatchSponsoringInstitutionModule'
            },
            {
                path: 'document',
                loadChildren: './document/document.module#JobmatchDocumentModule'
            },
            {
                path: 'company',
                loadChildren: './company/company.module#JobmatchCompanyModule'
            },
            {
                path: 'company-sector',
                loadChildren: './company-sector/company-sector.module#JobmatchCompanySectorModule'
            },
            {
                path: 'sponsoring-institution',
                loadChildren: './sponsoring-institution/sponsoring-institution.module#JobmatchSponsoringInstitutionModule'
            },
            {
                path: 'project',
                loadChildren: './project/project.module#JobmatchProjectModule'
            },
            {
                path: 'job-offer',
                loadChildren: './job-offer/job-offer.module#JobmatchJobOfferModule'
            },
            {
                path: 'job-offer-skill',
                loadChildren: './job-offer-skill/job-offer-skill.module#JobmatchJobOfferSkillModule'
            },
            {
                path: 'applied-job',
                loadChildren: './applied-job/applied-job.module#JobmatchAppliedJobModule'
            },
            {
                path: 'applied-job-iteration',
                loadChildren: './applied-job-iteration/applied-job-iteration.module#JobmatchAppliedJobIterationModule'
            },
            {
                path: 'project',
                loadChildren: './project/project.module#JobmatchProjectModule'
            },
            {
                path: 'job-offer',
                loadChildren: './job-offer/job-offer.module#JobmatchJobOfferModule'
            },
            {
                path: 'direct-application',
                loadChildren: './direct-application/direct-application.module#JobmatchDirectApplicationModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobmatchEntityModule {}
