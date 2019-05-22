import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { CompanySectorService } from './company-sector.service';

@Component({
    selector: 'jhi-company-sector-update',
    templateUrl: './company-sector-update.component.html'
})
export class CompanySectorUpdateComponent implements OnInit {
    companySector: ICompanySector;
    isSaving: boolean;
    createdDate: string;
    lastModifiedDate: string;

    constructor(protected companySectorService: CompanySectorService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ companySector }) => {
            this.companySector = companySector;
            this.createdDate = this.companySector.createdDate != null ? this.companySector.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.companySector.lastModifiedDate != null ? this.companySector.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.companySector.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.companySector.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.companySector.id !== undefined) {
            this.subscribeToSaveResponse(this.companySectorService.update(this.companySector));
        } else {
            this.subscribeToSaveResponse(this.companySectorService.create(this.companySector));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanySector>>) {
        result.subscribe((res: HttpResponse<ICompanySector>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
