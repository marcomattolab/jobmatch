import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IUser, UserService } from 'app/core';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICandidate } from 'app/shared/model/candidate.model';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CandidateService } from './candidate.service';

@Component({
    selector: 'jhi-candidate-update',
    templateUrl: './candidate-update.component.html'
})
export class CandidateUpdateComponent implements OnInit {
    candidate: ICandidate;
    isSaving: boolean;

    users: IUser[];
    createdDate: string;
    lastModifiedDate: string;
    birthdayDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected candidateService: CandidateService,
        protected userService: UserService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) { }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ candidate }) => {
            this.candidate = candidate;
            this.createdDate = this.candidate.createdDate != null ? this.candidate.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.candidate.lastModifiedDate != null ? this.candidate.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.candidate, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.candidate.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.candidate.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.candidate.id !== undefined) {
            this.subscribeToSaveResponse(this.candidateService.update(this.candidate));
        } else {
            this.subscribeToSaveResponse(this.candidateService.create(this.candidate));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidate>>) {
        result.subscribe((res: HttpResponse<ICandidate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
