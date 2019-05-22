import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IJobOffer } from 'app/shared/model/job-offer.model';
import { JobOfferService } from './job-offer.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { CompanySectorService } from 'app/entities/company-sector';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project';

@Component({
    selector: 'jhi-job-offer-update',
    templateUrl: './job-offer-update.component.html'
})
export class JobOfferUpdateComponent implements OnInit {
    jobOffer: IJobOffer;
    isSaving: boolean;

    companies: ICompany[];

    companysectors: ICompanySector[];

    projects: IProject[];
    createdDate: string;
    lastModifiedDate: string;
    startDateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected jobOfferService: JobOfferService,
        protected companyService: CompanyService,
        protected companySectorService: CompanySectorService,
        protected projectService: ProjectService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jobOffer }) => {
            this.jobOffer = jobOffer;
            this.createdDate = this.jobOffer.createdDate != null ? this.jobOffer.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate = this.jobOffer.lastModifiedDate != null ? this.jobOffer.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.companyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompany[]>) => response.body)
            )
            .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.companySectorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompanySector[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompanySector[]>) => response.body)
            )
            .subscribe((res: ICompanySector[]) => (this.companysectors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.projectService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProject[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProject[]>) => response.body)
            )
            .subscribe((res: IProject[]) => (this.projects = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.jobOffer.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.jobOffer.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.jobOffer.id !== undefined) {
            this.subscribeToSaveResponse(this.jobOfferService.update(this.jobOffer));
        } else {
            this.subscribeToSaveResponse(this.jobOfferService.create(this.jobOffer));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobOffer>>) {
        result.subscribe((res: HttpResponse<IJobOffer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCompanySectorById(index: number, item: ICompanySector) {
        return item.id;
    }

    trackProjectById(index: number, item: IProject) {
        return item.id;
    }
}
