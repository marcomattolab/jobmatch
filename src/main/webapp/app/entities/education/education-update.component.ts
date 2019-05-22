import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEducation } from 'app/shared/model/education.model';
import { EducationService } from './education.service';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate';

@Component({
    selector: 'jhi-education-update',
    templateUrl: './education-update.component.html'
})
export class EducationUpdateComponent implements OnInit {
    education: IEducation;
    isSaving: boolean;

    candidates: ICandidate[];
    createdDate: string;
    lastModifiedDate: string;
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected educationService: EducationService,
        protected candidateService: CandidateService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ education }) => {
            this.education = education;
            this.createdDate = this.education.createdDate != null ? this.education.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.education.lastModifiedDate != null ? this.education.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
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
        this.education.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.education.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.education.id !== undefined) {
            this.subscribeToSaveResponse(this.educationService.update(this.education));
        } else {
            this.subscribeToSaveResponse(this.educationService.create(this.education));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEducation>>) {
        result.subscribe((res: HttpResponse<IEducation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
