import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IJobExperience } from 'app/shared/model/job-experience.model';
import { JobExperienceService } from './job-experience.service';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate';

@Component({
    selector: 'jhi-job-experience-update',
    templateUrl: './job-experience-update.component.html'
})
export class JobExperienceUpdateComponent implements OnInit {
    jobExperience: IJobExperience;
    isSaving: boolean;

    candidates: ICandidate[];
    createdDate: string;
    lastModifiedDate: string;
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected jobExperienceService: JobExperienceService,
        protected candidateService: CandidateService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jobExperience }) => {
            this.jobExperience = jobExperience;
            this.createdDate = this.jobExperience.createdDate != null ? this.jobExperience.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.jobExperience.lastModifiedDate != null ? this.jobExperience.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.candidateService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICandidate[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICandidate[]>) => response.body)
            )
            .subscribe((res: ICandidate[]) => (this.candidates = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.jobExperience.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.jobExperience.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.jobExperience.id !== undefined) {
            this.subscribeToSaveResponse(this.jobExperienceService.update(this.jobExperience));
        } else {
            this.subscribeToSaveResponse(this.jobExperienceService.create(this.jobExperience));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobExperience>>) {
        result.subscribe((res: HttpResponse<IJobExperience>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
