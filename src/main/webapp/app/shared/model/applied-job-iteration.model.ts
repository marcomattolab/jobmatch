import { Moment } from 'moment';

export interface IAppliedJobIteration {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    iterationDate?: Moment;
    iterationType?: string;
    iterationNote?: any;
    appliedJobId?: number;
}

export class AppliedJobIteration implements IAppliedJobIteration {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public iterationDate?: Moment,
        public iterationType?: string,
        public iterationNote?: any,
        public appliedJobId?: number
    ) {}
}
