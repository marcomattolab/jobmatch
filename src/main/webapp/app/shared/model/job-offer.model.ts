import { Moment } from 'moment';
import { IJobOfferSkill } from './job-offer-skill.model';
import { Country } from './candidate.model';
import { ISkillTag } from './skill-tag.model';

export const enum EmploymentType {
    FULL_TIME = 'FULL_TIME',
    PART_TIME = 'PART_TIME'
}

export const enum SeniorityLevel {
    BEGINNER = 'BEGINNER',
    MEDIUM = 'MEDIUM',
    EXPERT = 'EXPERT'
}

export const enum JobOfferStatus {
    DRAFT = 'DRAFT',
    ACTIVE = 'ACTIVE',
    ENDED = 'ENDED'
}

export enum JobOfferType {
    JOB = 'JOB',
    STAGE = 'STAGE'
}

export interface IJobOffer {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    startDate?: Moment;
    jobTitle?: string;
    jobShortDescription?: string;
    jobDescription?: any;
    jobOfferType?: JobOfferType;
    responsibilitiesDescription?: any;
    experiencesDescription?: any;
    attributesDescription?: any;
    jobFunctions?: string;
    jobCity?: string;
    jobCountry?: Country;
    employmentType?: EmploymentType;
    seniorityLevel?: SeniorityLevel;
    salaryOffered?: string;
    status?: JobOfferStatus;
    enabled?: boolean;
    skills?: IJobOfferSkill[];
    companyId?: number;
    companyName?: string;
    imageUrl?: string;
    sectorId?: number;
    sectorDescription?: string;
    projectId?: number;
    timePassed?: string;
    candidateApplied?: boolean;
    applymentCount?: number;
    editAvailable?: boolean;
    deleteAvailable?: boolean;
    appliedJobAvailable?: boolean;
}

export class JobOffer implements IJobOffer {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public startDate?: Moment,
        public jobTitle?: string,
        public jobShortDescription?: string,
        public jobDescription?: any,
        public jobOfferType?: JobOfferType,
        public responsibilitiesDescription?: any,
        public experiencesDescription?: any,
        public attributesDescription?: any,
        public jobFunctions?: string,
        public jobCity?: string,
        public jobCountry?: Country,
        public employmentType?: EmploymentType,
        public seniorityLevel?: SeniorityLevel,
        public salaryOffered?: string,
        public status?: JobOfferStatus,
        public enabled?: boolean,
        public skills?: IJobOfferSkill[],
        public imageUrl?: string,
        public companyId?: number,
        public companyName?: string,
        public sectorId?: number,
        public sectorDescription?: string,
        public projectId?: number,
        public timePassed?: string,
        public candidateApplied?: boolean,
        public applymentCount?: number,
        public editAvailable?: boolean,
        public deleteAvailable?: boolean,
        public appliedJobAvailable?: boolean,
        public selectedInList?: boolean

    ) {
        this.enabled = this.enabled || false;
        this.candidateApplied = this.candidateApplied || false;
        this.editAvailable = this.editAvailable || false;
        this.deleteAvailable = this.deleteAvailable || false;
        this.appliedJobAvailable = this.appliedJobAvailable || false;
        this.selectedInList = this.selectedInList || false;
    }
}

export class JobOfferSearchFilter {
    constructor(
        public skillsTag?: ISkillTag[],
        public skillTagName?: string,
        public jobTitle?: string,
        public jobCountry?: string,
        public jobCity?: string,
        public employmentType?: EmploymentType,
        public seniorityLevel?: SeniorityLevel,
        public companyId?: number,
        public sectorId?: number,
        public jobOfferSectorName?: string,
        public jobOfferType?: JobOfferType,
        // public status?: JobOfferStatus,
        public statusDraft?: boolean,
        public statusActive?: boolean,
        public statusEnded?: boolean,
        public includeOthersCompanies?: boolean,
        public startDate?: Moment
    ) {
        this.skillsTag = [];
        this.statusDraft = true;
        this.statusActive = true;
        this.statusEnded = true;
        this.includeOthersCompanies = false;
    }
}

export class ChangeJobStateRequest {
    constructor(
        public jobOfferId?: number,
        public status?: JobOfferStatus
    ) { }
}

export class PromoteJobOfferCustomMessage {
    constructor(
        public langKey: string,
        public customMessage: string
    ) { }
}

export class PromoteJobOffer {
    constructor(
        public jobOffersId: number[],
        public candidatesId: number[],
        public customMessages: PromoteJobOfferCustomMessage[]
    ) { }
}
