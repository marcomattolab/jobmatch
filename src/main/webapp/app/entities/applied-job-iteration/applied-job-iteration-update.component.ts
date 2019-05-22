import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';
import { AppliedJobIterationService } from './applied-job-iteration.service';
import { IAppliedJob } from 'app/shared/model/applied-job.model';
import { AppliedJobService } from 'app/entities/applied-job';

@Component({
    selector: 'jhi-applied-job-iteration-update',
    templateUrl: './applied-job-iteration-update.component.html'
})
export class AppliedJobIterationUpdateComponent implements OnInit {
    appliedJobIteration: IAppliedJobIteration;
    isSaving: boolean;

    appliedjobs: IAppliedJob[];
    createdDate: string;
    lastModifiedDate: string;
    iterationDate: string;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected appliedJobIterationService: AppliedJobIterationService,
        protected appliedJobService: AppliedJobService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appliedJobIteration }) => {
            this.appliedJobIteration = appliedJobIteration;
            this.createdDate =
                this.appliedJobIteration.createdDate != null ? this.appliedJobIteration.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.appliedJobIteration.lastModifiedDate != null
                    ? this.appliedJobIteration.lastModifiedDate.format(DATE_TIME_FORMAT)
                    : null;
            this.iterationDate =
                this.appliedJobIteration.iterationDate != null ? this.appliedJobIteration.iterationDate.format(DATE_TIME_FORMAT) : null;
        });
        this.appliedJobService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAppliedJob[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAppliedJob[]>) => response.body)
            )
            .subscribe((res: IAppliedJob[]) => (this.appliedjobs = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.appliedJobIteration.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.appliedJobIteration.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        this.appliedJobIteration.iterationDate = this.iterationDate != null ? moment(this.iterationDate, DATE_TIME_FORMAT) : null;
        if (this.appliedJobIteration.id !== undefined) {
            this.subscribeToSaveResponse(this.appliedJobIterationService.update(this.appliedJobIteration));
        } else {
            this.subscribeToSaveResponse(this.appliedJobIterationService.create(this.appliedJobIteration));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppliedJobIteration>>) {
        result.subscribe((res: HttpResponse<IAppliedJobIteration>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAppliedJobById(index: number, item: IAppliedJob) {
        return item.id;
    }
}
