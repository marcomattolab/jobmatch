import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAppliedJob } from 'app/shared/model/applied-job.model';
import { AppliedJobService } from './applied-job.service';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { IJobOffer } from 'app/shared/model/job-offer.model';
import { JobOfferService } from 'app/entities/job-offer';

@Component({
    selector: 'jhi-applied-job-update',
    templateUrl: './applied-job-update.component.html'
})
export class AppliedJobUpdateComponent implements OnInit {
    appliedJob: IAppliedJob;
    isSaving: boolean;

    candidates: ICandidate[];

    joboffers: IJobOffer[];
    createdDate: string;
    lastModifiedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected appliedJobService: AppliedJobService,
        protected candidateService: CandidateService,
        protected jobOfferService: JobOfferService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appliedJob }) => {
            this.appliedJob = appliedJob;
            this.createdDate = this.appliedJob.createdDate != null ? this.appliedJob.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.appliedJob.lastModifiedDate != null ? this.appliedJob.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.candidateService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICandidate[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICandidate[]>) => response.body)
            )
            .subscribe((res: ICandidate[]) => (this.candidates = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.jobOfferService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IJobOffer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IJobOffer[]>) => response.body)
            )
            .subscribe((res: IJobOffer[]) => (this.joboffers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.appliedJob.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.appliedJob.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.appliedJob.id !== undefined) {
            this.subscribeToSaveResponse(this.appliedJobService.update(this.appliedJob));
        } else {
            this.subscribeToSaveResponse(this.appliedJobService.create(this.appliedJob));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppliedJob>>) {
        result.subscribe((res: HttpResponse<IAppliedJob>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCandidateById(index: number, item: ICandidate) {
        return item.id;
    }

    trackJobOfferById(index: number, item: IJobOffer) {
        return item.id;
    }
}
