import { Moment } from 'moment';

export const enum AppiedJobStatus {
    NEW = 'NEW',
    EVALUATION_IN_PROGRESS = 'EVALUATION_IN_PROGRESS',
    APPROVED = 'APPROVED',
    REJECTED = 'REJECTED'
}

export interface IDirectApplication {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    appiedJobStatus?: AppiedJobStatus;
    companyId?: number;
    candidateId?: number;
}

export class DirectApplication implements IDirectApplication {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public appiedJobStatus?: AppiedJobStatus,
        public companyId?: number,
        public candidateId?: number
    ) {}
}
