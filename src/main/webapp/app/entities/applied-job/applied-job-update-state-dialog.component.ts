import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {PlatformLocation} from '@angular/common';
import { AppiedJobStatus, AppliedJob, AppliedJobFeedback } from 'app/shared/model/applied-job.model';
import { AppliedJobService } from './applied-job.service';
import { pipeToResponse } from 'app/shared';

@Component({
    selector: 'jhi-applied-job-update-state-dialog',
    templateUrl: './applied-job-update-state-dialog.component.html'
})
export class AppliedJobUpdateStateDialogComponent implements OnInit {
    nextState: AppiedJobStatus;
    appliedJobId: number;
    appliedJob: AppliedJob = {};
    readOnly = false;

    constructor(
        private activeModal: NgbActiveModal,
        private appliedJobService: AppliedJobService,
        private location: PlatformLocation) {

        location.onPopState(() => this.activeModal.dismiss());
    }

    ngOnInit() {
        pipeToResponse(this.appliedJobService.find(this.appliedJobId)).subscribe(
            (appl: AppliedJob) => this.appliedJob = appl
        );
    }

    clear() {
        this.activeModal.dismiss();
    }

    confirm() {
        this.appliedJob.appiedJobStatus = this.nextState;
        pipeToResponse(this.appliedJobService.update(this.appliedJob)).subscribe(
            (newApplied: AppliedJob) => this.activeModal.close(newApplied),
            err => this.activeModal.dismiss(err)
        );
    }
}
