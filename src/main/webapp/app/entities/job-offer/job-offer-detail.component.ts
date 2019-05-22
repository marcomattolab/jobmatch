import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { isValidId, enableFooter, disableFooter } from 'app/shared';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { ModalService } from 'app/shared/modal/modal.service';
import { ApplyToJobRequest, IAppliedJob } from 'app/shared/model/applied-job.model';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { CompanyInfo } from 'app/shared/model/company.model';
import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';
import { IJobOffer, JobOfferStatus } from 'app/shared/model/job-offer.model';
import { ISkill } from 'app/shared/model/skill.model';
import { observableArrayToPromiseNoThrow, pipeToResponse } from 'app/shared/util/request-util';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs/internal/Subscription';
import { AppliedJobService } from '../applied-job/applied-job.service';
import { CompanySectorService } from '../company-sector/company-sector.service';
import { CompanyChangeStateJobOfferDialogComponent } from '../company/company-change-state-job-offer-dialog.component';
import { CompanyDeleteJobOfferDialogComponent } from '../company/company-delete-job-offer-dialog.component';
import { CompanyUpdateJobOfferDialogComponent } from '../company/company-update-job-offer-dialog.component';
import { CompanyService } from '../company/company.service';
import { ProjectService } from '../project/project.service';
import { SkillService } from '../skill/skill.service';
import { JobOfferService } from './job-offer.service';

@Component({
    selector: 'jhi-job-offer-detail',
    templateUrl: './job-offer-detail.component.html'
})
export class JobOfferDetailComponent implements OnInit, OnDestroy {
    jobOffer: IJobOffer;
    jobCompanyInfo: CompanyInfo;
    candidateSkills: ISkill[];
    selectedDeletedEventSubscriber: Subscription;
    selectedModifiedEventSubscriber: Subscription;
    userAuthority: string;

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected companyService: CompanyService,
        protected jobOfferService: JobOfferService,
        protected modalService: ModalService,
        protected companySectorsService: CompanySectorService,
        protected projectService: ProjectService,
        protected eventManager: JhiEventManager,
        protected appliedJobService: AppliedJobService,
        protected accountService: AccountService,
        protected companySectorService: CompanySectorService,
        protected skillService: SkillService) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobOffer }) => {
            this.jobOffer = jobOffer;
        });

        this.userAuthority = this.accountService.getCurrentRole();

        this.reloadAppliedJobs();
        this.initCandidateSkills();

        pipeToResponse(this.appliedJobService.count({ 'jobOfferId.equals': this.jobOffer.id })).subscribe(
            (count: number) => {
                this.jobOffer.applymentCount = count;
            }
        );

        pipeToResponse(this.companyService.findInfo(this.jobOffer.companyId)).subscribe(
            (info: CompanyInfo) => {
                this.jobCompanyInfo = info;
                this.jobOffer.imageUrl = info.imageUrl; // this.helper.loadImage( this.jobOffer.imageUrl );
            }
        );

        this.selectedDeletedEventSubscriber = this.eventManager.subscribe('jobOfferDeleted', response => this.refreshJobSelectedOnDelete());
        this.selectedModifiedEventSubscriber = this.eventManager.subscribe('jobOfferModified', response => this.reloadJobSelected());
        enableFooter(document);
    }

    reloadAppliedJobs() {
        if (this.userAuthority === AuthoritiesConstants.ROLE_CANDIDATE) {
            pipeToResponse(this.appliedJobService.query({ 'candidateId.equals': this.accountService.getCurrentRoleId(), 'jobOfferId.equals': this.jobOffer.id }))
                .subscribe((resAppliedJobs: IAppliedJob[]) => {
                    this.jobOffer.candidateApplied = resAppliedJobs && resAppliedJobs.length === 1;
                });
        }
    }

    ngOnDestroy(): void {
        this.eventManager.destroy(this.selectedDeletedEventSubscriber);
        this.eventManager.destroy(this.selectedModifiedEventSubscriber);
        disableFooter(document);
    }

    refreshJobSelectedOnDelete() {
        this.previousState();
    }

    initCandidateSkills() {
        if (this.userAuthority === AuthoritiesConstants.ROLE_CANDIDATE) {
            pipeToResponse(this.skillService.findAll(this.accountService.getCurrentRoleId())).subscribe((res: ISkill[]) => {
                this.candidateSkills = res;
                if (this.jobOffer.skills) {
                    this.jobOffer.skills.map(jobSkill => {
                        const candidateSkill = this.candidateSkills.find(cs => cs.skillTagId === jobSkill.skillTagId);
                        if (candidateSkill) {
                            jobSkill.candidateOwner = true;
                        }
                    });
                    this.sortSkills(this.jobOffer.skills);
                }
            });
        }
    }

    reloadJobSelected() {
        pipeToResponse(this.jobOfferService.find(this.jobOffer.id)).subscribe(
            (res: IJobOffer) => {
                res.imageUrl = this.jobOffer.imageUrl;
                res.applymentCount = this.jobOffer.applymentCount;
                this.jobOffer = res;
                pipeToResponse(this.companyService.findInfo(this.jobOffer.companyId)).subscribe(
                    (info: CompanyInfo) => {
                        this.jobCompanyInfo = info;
                    }
                );

                if (this.jobOffer.sectorId) {
                    pipeToResponse(this.companySectorService.find(this.jobOffer.sectorId)).subscribe(
                        (cs: ICompanySector) => this.jobOffer.sectorDescription = cs.description
                    );
                }
                if (this.accountService.isCurrentRoleCandidate() && this.candidateSkills) {
                    this.jobOffer.skills.map(jobSkill => {
                        const candidateSkill = this.candidateSkills.find(cs => cs.skillTagId === jobSkill.skillTagId);
                        if (candidateSkill) {
                            jobSkill.candidateOwner = true;
                        }
                    });
                    this.sortSkills(this.jobOffer.skills);
                }
            }
        );
    }

    sortSkills(skills: IJobOfferSkill[]) {
        skills.sort((a: IJobOfferSkill, b: IJobOfferSkill) => {
            if (a.candidateOwner && !b.candidateOwner) {
                return -1;
            } else if (b.candidateOwner && !a.candidateOwner) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    async onModifyClick() {
        const isSaving = !isValidId(this.jobOffer.companyId);
        const companySectors = await observableArrayToPromiseNoThrow(pipeToResponse(this.companySectorsService.findAll()));
        const projects = await observableArrayToPromiseNoThrow(
            pipeToResponse(this.projectService.query({ 'companyId.equals': this.jobOffer.companyId })));

        const params = {
            'companyId': this.jobOffer.companyId,
            'jobOffer': Object.assign({}, this.jobOffer),
            'companySectors': companySectors,
            'projects': projects,
            'isSaving': isSaving
        };

        this.modalService.openHugeModal(CompanyUpdateJobOfferDialogComponent, params)
            .then(res => { })
            .catch(err => { });
    }

    onDeleteClick() {
        const params = {
            'jobOffer': this.jobOffer
        };

        this.modalService.openHugeModal(CompanyDeleteJobOfferDialogComponent, params)
            .then(res => { })
            .catch(err => { });
    }

    onPublishOfferClick() {
        const params = {
            'jobOffer': this.jobOffer,
            'status': JobOfferStatus.ACTIVE,
        };

        this.modalService.openHugeModal(CompanyChangeStateJobOfferDialogComponent, params)
            .then(res => { })
            .catch(err => { });
    }

    numberOfCandidateSkillOwner() {
        let numberOfCandidateSkillOwner = 0;
        if (this.jobOffer && this.jobOffer.skills && this.jobOffer.skills.length > 0) {
            numberOfCandidateSkillOwner = this.jobOffer.skills.filter(skill => skill.candidateOwner).length;
        }
        return numberOfCandidateSkillOwner;
    }

    onEndOfferClick() {
        const params = {
            'jobOffer': this.jobOffer,
            'status': JobOfferStatus.ENDED,
        };

        this.modalService.openHugeModal(CompanyChangeStateJobOfferDialogComponent, params)
            .then(res => { })
            .catch(err => { });
    }

    onApplyJobClick() {
        if (this.userAuthority === AuthoritiesConstants.ROLE_CANDIDATE && this.jobOffer.appliedJobAvailable) {
            const id = this.accountService.getCurrentRoleId();
            pipeToResponse(this.appliedJobService.applyToJob(new ApplyToJobRequest(id, this.jobOffer.id))).subscribe(
                res => this.jobOffer.candidateApplied = true,
                err => this.jobOffer.candidateApplied = false
            );
        }
    }

    previousState() {
        window.history.back();
    }
}
