import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';

@Component({
    selector: 'jhi-job-offer-skill-detail',
    templateUrl: './job-offer-skill-detail.component.html'
})
export class JobOfferSkillDetailComponent implements OnInit {
    jobOfferSkill: IJobOfferSkill;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobOfferSkill }) => {
            this.jobOfferSkill = jobOfferSkill;
        });
    }

    previousState() {
        window.history.back();
    }
}
