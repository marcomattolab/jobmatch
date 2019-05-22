import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from './skill.service';
import { ISkillTag } from 'app/shared/model/skill-tag.model';
import { SkillTagService } from 'app/entities/skill-tag';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate';

@Component({
    selector: 'jhi-skill-update',
    templateUrl: './skill-update.component.html'
})
export class SkillUpdateComponent implements OnInit {
    skill: ISkill;
    isSaving: boolean;

    skilltags: ISkillTag[];

    candidates: ICandidate[];
    createdDate: string;
    lastModifiedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected skillService: SkillService,
        protected skillTagService: SkillTagService,
        protected candidateService: CandidateService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ skill }) => {
            this.skill = skill;
            this.createdDate = this.skill.createdDate != null ? this.skill.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate = this.skill.lastModifiedDate != null ? this.skill.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.skillTagService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISkillTag[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISkillTag[]>) => response.body)
            )
            .subscribe((res: ISkillTag[]) => (this.skilltags = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.skill.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.skill.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.skill.id !== undefined) {
            this.subscribeToSaveResponse(this.skillService.update(this.skill));
        } else {
            this.subscribeToSaveResponse(this.skillService.create(this.skill));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkill>>) {
        result.subscribe((res: HttpResponse<ISkill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCandidateById(index: number, item: ICandidate) {
        return item.id;
    }
}
