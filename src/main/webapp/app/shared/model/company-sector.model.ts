import { Moment } from 'moment';

export interface ICompanySector {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    code?: string;
    description?: string;
    descriptionEN?: string;
    descriptionIT?: string;
}

export class CompanySector implements ICompanySector {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public code?: string,
        public description?: string,
        public descriptionEN?: string,
        public descriptionIT?: string
    ) {}
}
