import { Moment } from 'moment';
import { SkillType } from './skill-tag.model';

export enum SkillLevelType {
    BEGINNER = 'BEGINNER',
    MEDIUM = 'MEDIUM',
    HIGTH = 'HIGTH',
    EXPERT = 'EXPERT'
}

export const enum ProficNumberOfYear {
    ZERO_TO_ONE = 'ZERO_TO_ONE',
    ONE_TO_THREE = 'ONE_TO_THREE',
    THREE_TO_FIVE = 'THREE_TO_FIVE',
    FIVE_TO_TEN = 'FIVE_TO_TEN',
    MORE_THAN_TEN = 'MORE_THAN_TEN'
}

export interface ICompanySkill {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    level?: SkillLevelType;
    proficNumberOfYear?: ProficNumberOfYear;
    skillTagId?: number;
    skillTagName?: string;
    skillTagType?: SkillType;
    companyId?: number;
}

export class CompanySkill implements ICompanySkill {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public level?: SkillLevelType,
        public proficNumberOfYear?: ProficNumberOfYear,
        public skillTagId?: number,
        public skillTagName?: string,
        public skillTagType?: SkillType,
        public companyId?: number
    ) {}
}
