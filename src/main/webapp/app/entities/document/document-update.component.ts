import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate';

@Component({
    selector: 'jhi-document-update',
    templateUrl: './document-update.component.html'
})
export class DocumentUpdateComponent implements OnInit {
    document: IDocument;
    isSaving: boolean;

    candidates: ICandidate[];
    createdDate: string;
    lastModifiedDate: string;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected documentService: DocumentService,
        protected candidateService: CandidateService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ document }) => {
            this.document = document;
            this.createdDate = this.document.createdDate != null ? this.document.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate = this.document.lastModifiedDate != null ? this.document.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
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
        this.document.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.document.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.document.id !== undefined) {
            this.subscribeToSaveResponse(this.documentService.update(this.document));
        } else {
            this.subscribeToSaveResponse(this.documentService.create(this.document));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>) {
        result.subscribe((res: HttpResponse<IDocument>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
