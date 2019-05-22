import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISkillTag } from 'app/shared/model/skill-tag.model';
import { SkillTagService } from './skill-tag.service';

@Component({
    selector: 'jhi-skill-tag-update',
    templateUrl: './skill-tag-update.component.html'
})
export class SkillTagUpdateComponent implements OnInit {
    skillTag: ISkillTag;
    isSaving: boolean;
    createdDate: string;
    lastModifiedDate: string;

    constructor(protected skillTagService: SkillTagService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ skillTag }) => {
            this.skillTag = skillTag;
            this.createdDate = this.skillTag.createdDate != null ? this.skillTag.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate = this.skillTag.lastModifiedDate != null ? this.skillTag.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.skillTag.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.skillTag.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.skillTag.id !== undefined) {
            this.subscribeToSaveResponse(this.skillTagService.update(this.skillTag));
        } else {
            this.subscribeToSaveResponse(this.skillTagService.create(this.skillTag));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkillTag>>) {
        result.subscribe((res: HttpResponse<ISkillTag>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
