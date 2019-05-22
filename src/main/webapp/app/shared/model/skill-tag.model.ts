import { Moment } from 'moment';

export const enum SkillType {
    LANGUAGE = 'LANGUAGE',
    OTHER = 'OTHER'
}

export interface ISkillTag {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    name?: string;
    type?: SkillType;
}

export class SkillTag implements ISkillTag {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public name?: string,
        public type?: SkillType
    ) {}
}
