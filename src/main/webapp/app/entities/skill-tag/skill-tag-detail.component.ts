import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkillTag } from 'app/shared/model/skill-tag.model';

@Component({
    selector: 'jhi-skill-tag-detail',
    templateUrl: './skill-tag-detail.component.html'
})
export class SkillTagDetailComponent implements OnInit {
    skillTag: ISkillTag;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ skillTag }) => {
            this.skillTag = skillTag;
        });
    }

    previousState() {
        window.history.back();
    }
}
