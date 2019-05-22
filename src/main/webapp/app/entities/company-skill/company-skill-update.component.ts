import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICompanySkill } from 'app/shared/model/company-skill.model';
import { CompanySkillService } from './company-skill.service';
import { ISkillTag } from 'app/shared/model/skill-tag.model';
import { SkillTagService } from 'app/entities/skill-tag';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
    selector: 'jhi-company-skill-update',
    templateUrl: './company-skill-update.component.html'
})
export class CompanySkillUpdateComponent implements OnInit {
    companySkill: ICompanySkill;
    isSaving: boolean;

    skilltags: ISkillTag[];

    companies: ICompany[];
    createdDate: string;
    lastModifiedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected companySkillService: CompanySkillService,
        protected skillTagService: SkillTagService,
        protected companyService: CompanyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ companySkill }) => {
            this.companySkill = companySkill;
            this.createdDate = this.companySkill.createdDate != null ? this.companySkill.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.companySkill.lastModifiedDate != null ? this.companySkill.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.skillTagService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISkillTag[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISkillTag[]>) => response.body)
            )
            .subscribe((res: ISkillTag[]) => (this.skilltags = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.companyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompany[]>) => response.body)
            )
            .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.companySkill.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.companySkill.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.companySkill.id !== undefined) {
            this.subscribeToSaveResponse(this.companySkillService.update(this.companySkill));
        } else {
            this.subscribeToSaveResponse(this.companySkillService.create(this.companySkill));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanySkill>>) {
        result.subscribe((res: HttpResponse<ICompanySkill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCompanyById(index: number, item: ICompany) {
        return item.id;
    }
}
