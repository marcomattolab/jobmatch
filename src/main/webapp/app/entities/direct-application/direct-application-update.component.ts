import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDirectApplication } from 'app/shared/model/direct-application.model';
import { DirectApplicationService } from './direct-application.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate';

@Component({
    selector: 'jhi-direct-application-update',
    templateUrl: './direct-application-update.component.html'
})
export class DirectApplicationUpdateComponent implements OnInit {
    directApplication: IDirectApplication;
    isSaving: boolean;

    companies: ICompany[];

    candidates: ICandidate[];
    createdDate: string;
    lastModifiedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected directApplicationService: DirectApplicationService,
        protected companyService: CompanyService,
        protected candidateService: CandidateService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ directApplication }) => {
            this.directApplication = directApplication;
            this.createdDate =
                this.directApplication.createdDate != null ? this.directApplication.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.directApplication.lastModifiedDate != null ? this.directApplication.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.companyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompany[]>) => response.body)
            )
            .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.candidateService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICandidate[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICandidate[]>) => response.body)
            )
            .subscribe((res: ICandidate[]) => (this.candidates = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.directApplication.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.directApplication.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.directApplication.id !== undefined) {
            this.subscribeToSaveResponse(this.directApplicationService.update(this.directApplication));
        } else {
            this.subscribeToSaveResponse(this.directApplicationService.create(this.directApplication));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDirectApplication>>) {
        result.subscribe((res: HttpResponse<IDirectApplication>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCompanyById(index: number, item: ICompany) {
        return item.id;
    }

    trackCandidateById(index: number, item: ICandidate) {
        return item.id;
    }
}
