import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';
import { SponsoringInstitutionService } from './sponsoring-institution.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-sponsoring-institution-update',
    templateUrl: './sponsoring-institution-update.component.html'
})
export class SponsoringInstitutionUpdateComponent implements OnInit {
    sponsoringInstitution: ISponsoringInstitution;
    isSaving: boolean;

    users: IUser[];
    createdDate: string;
    lastModifiedDate: string;
    foundationDateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected sponsoringInstitutionService: SponsoringInstitutionService,
        protected userService: UserService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sponsoringInstitution }) => {
            this.sponsoringInstitution = sponsoringInstitution;
            this.createdDate =
                this.sponsoringInstitution.createdDate != null ? this.sponsoringInstitution.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.sponsoringInstitution.lastModifiedDate != null
                    ? this.sponsoringInstitution.lastModifiedDate.format(DATE_TIME_FORMAT)
                    : null;
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
        this.dataUtils.clearInputImage(this.sponsoringInstitution, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.sponsoringInstitution.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.sponsoringInstitution.lastModifiedDate =
            this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.sponsoringInstitution.id !== undefined) {
            this.subscribeToSaveResponse(this.sponsoringInstitutionService.update(this.sponsoringInstitution));
        } else {
            this.subscribeToSaveResponse(this.sponsoringInstitutionService.create(this.sponsoringInstitution));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISponsoringInstitution>>) {
        result.subscribe(
            (res: HttpResponse<ISponsoringInstitution>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
