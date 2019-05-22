import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';
import { JobOfferSkillService } from './job-offer-skill.service';
import { ISkillTag } from 'app/shared/model/skill-tag.model';
import { SkillTagService } from 'app/entities/skill-tag';
import { IJobOffer } from 'app/shared/model/job-offer.model';
import { JobOfferService } from 'app/entities/job-offer';

@Component({
    selector: 'jhi-job-offer-skill-update',
    templateUrl: './job-offer-skill-update.component.html'
})
export class JobOfferSkillUpdateComponent implements OnInit {
    jobOfferSkill: IJobOfferSkill;
    isSaving: boolean;

    skilltags: ISkillTag[];

    joboffers: IJobOffer[];
    createdDate: string;
    lastModifiedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected jobOfferSkillService: JobOfferSkillService,
        protected skillTagService: SkillTagService,
        protected jobOfferService: JobOfferService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jobOfferSkill }) => {
            this.jobOfferSkill = jobOfferSkill;
            this.createdDate = this.jobOfferSkill.createdDate != null ? this.jobOfferSkill.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.jobOfferSkill.lastModifiedDate != null ? this.jobOfferSkill.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.skillTagService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISkillTag[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISkillTag[]>) => response.body)
            )
            .subscribe((res: ISkillTag[]) => (this.skilltags = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.jobOfferSkill.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.jobOfferSkill.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.jobOfferSkill.id !== undefined) {
            this.subscribeToSaveResponse(this.jobOfferSkillService.update(this.jobOfferSkill));
        } else {
            this.subscribeToSaveResponse(this.jobOfferSkillService.create(this.jobOfferSkill));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobOfferSkill>>) {
        result.subscribe((res: HttpResponse<IJobOfferSkill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSkillTagById(index: number, item: ISkillTag) {
        return item.id;
    }

    trackJobOfferById(index: number, item: IJobOffer) {
        return item.id;
    }
}
