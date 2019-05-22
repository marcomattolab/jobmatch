import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppliedJob } from 'app/shared/model/applied-job.model';

@Component({
    selector: 'jhi-applied-job-detail',
    templateUrl: './applied-job-detail.component.html'
})
export class AppliedJobDetailComponent implements OnInit {
    appliedJob: IAppliedJob;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appliedJob }) => {
            this.appliedJob = appliedJob;
        });
    }

    previousState() {
        window.history.back();
    }
}
