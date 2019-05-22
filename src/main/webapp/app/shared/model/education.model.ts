import { Moment } from 'moment';

export const enum EducationType {
    EDUCATION = 'EDUCATION',
    CERTIFICATION = 'CERTIFICATION'
}

export interface IEducation {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    schoolName?: string;
    fieldOfStudy?: string;
    description?: any;
    startDate?: Moment;
    endDate?: Moment;
    current?: boolean;
    expires?: boolean;
    educationType?: EducationType;
    candidateId?: number;
}

export class Education implements IEducation {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public schoolName?: string,
        public fieldOfStudy?: string,
        public description?: any,
        public startDate?: Moment,
        public endDate?: Moment,
        public current?: boolean,
        public expires?: boolean,
        public educationType?: EducationType,
        public candidateId?: number
    ) {
        this.current = this.current || false;
        this.expires = this.expires || false;
    }
}
