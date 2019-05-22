import { Moment } from 'moment';
import { Country } from './candidate.model';

export interface IJobExperience {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    jobTitle?: string;
    jobDescription?: any;
    companyName?: string;
    startDate?: Moment;
    endDate?: Moment;
    current?: boolean;
    enabled?: boolean;
    candidateId?: number;
    city?: string;
    country?: Country;
}

export class JobExperience implements IJobExperience {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public jobTitle?: string,
        public jobDescription?: any,
        public companyName?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public current?: boolean,
        public enabled?: boolean,
        public candidateId?: number,
        public city?: string,
        public country?: Country,
    ) {
        this.current = this.current || false;
        this.enabled = this.enabled || false;
    }
}
