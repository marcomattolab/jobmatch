import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';

@Component({
    selector: 'jhi-applied-job-iteration-detail',
    templateUrl: './applied-job-iteration-detail.component.html'
})
export class AppliedJobIterationDetailComponent implements OnInit {
    appliedJobIteration: IAppliedJobIteration;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appliedJobIteration }) => {
            this.appliedJobIteration = appliedJobIteration;
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
