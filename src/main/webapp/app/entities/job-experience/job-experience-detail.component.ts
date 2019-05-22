import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IJobExperience } from 'app/shared/model/job-experience.model';

@Component({
    selector: 'jhi-job-experience-detail',
    templateUrl: './job-experience-detail.component.html'
})
export class JobExperienceDetailComponent implements OnInit {
    jobExperience: IJobExperience;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobExperience }) => {
            this.jobExperience = jobExperience;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
