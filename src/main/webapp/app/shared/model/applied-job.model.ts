import * as moment from 'moment';
import { GenderType } from './candidate.model';
import { IAppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';

export const enum AppliedJobFeedback {
    INSUFFICIENT = 'INSUFFICIENT',
    FAIRLY_GOOD = 'FAIRLY_GOOD',
    GOOD = 'GOOD',
    EXCELLENT = 'EXCELLENT'
}

export const enum AppiedJobStatus {
    NEW = 'NEW',
    EVALUATION_IN_PROGRESS = 'EVALUATION_IN_PROGRESS',
    APPROVED = 'APPROVED',
    REJECTED = 'REJECTED'
}

export interface IAppliedJob {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: moment.Moment;
    lastModifiedDate?: moment.Moment;
    appliedJobFeedback?: AppliedJobFeedback;
    feedbackNote?: string;
    appiedJobStatus?: AppiedJobStatus;
    appliedJobIterations?: IAppliedJobIteration[];
    candidateId?: number;
    jobOfferId?: number;
}

export class AppliedJob implements IAppliedJob {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: moment.Moment,
        public lastModifiedDate?: moment.Moment,
        public appliedJobFeedback?: AppliedJobFeedback,
        public feedbackNote?: string,
        public appiedJobStatus?: AppiedJobStatus,
        public appliedJobIterations?: IAppliedJobIteration[],
        public candidateId?: number,
        public jobOfferId?: number
    ) { }
}

export class AppliedJobItem {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: moment.Moment,
        public lastModifiedDate?: moment.Moment,
        public appiedJobStatus?: AppiedJobStatus,
        public appliedJobFeedback?: AppliedJobFeedback,
        public candidateId?: number,
        public firstName?: string,
        public lastName?: string,
        public jobOfferTitle?: string,
        public jobOfferId?: number,
        public jobOfferCountry?: string,
        public jobOfferCity?: string,
        public companyId?: number,
        public companyName?: string,
        public imageUrl?: string,
    ) { }
}

export class ApplyToJobRequest {
    constructor(
        public candidateId?: number,
        public jobOfferId?: number
    ) { }
}

export class AppliedJobOfferSearchFilter {
    constructor(
        public jobOfferId?: number,
        public jobOfferTitle?: string,
        public statusNew?: boolean,
        public statusInProgress?: boolean,
        public statusAprroved?: boolean,
        public statusRejected?: boolean
    ) {
        this.statusNew = true;
        this.statusInProgress = true;
        this.statusAprroved = true;
        this.statusRejected = true;
    }
}
