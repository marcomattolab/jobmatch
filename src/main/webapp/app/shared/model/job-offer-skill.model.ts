import { Moment } from 'moment';

export const enum SkillLevelType {
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

export interface IJobOfferSkill {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    level?: SkillLevelType;
    proficNumberOfYear?: ProficNumberOfYear;
    skillTagName?: string;
    skillTagId?: number;
    jobOfferId?: number;
    candidateOwner?: boolean;
}

export class JobOfferSkill implements IJobOfferSkill {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public level?: SkillLevelType,
        public proficNumberOfYear?: ProficNumberOfYear,
        public skillTagName?: string,
        public skillTagId?: number,
        public jobOfferId?: number,
        public candidateOwner?: boolean
    ) {
        this.candidateOwner = this.candidateOwner || false;
    }
}
