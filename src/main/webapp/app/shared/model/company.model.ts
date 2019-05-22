import { Country } from 'app/shared/model/candidate.model';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { ICompanySkill } from 'app/shared/model/company-skill.model';
import { Moment } from 'moment';

export const enum CompanySizeType {
    SMALL = 'SMALL',
    MEDIUM = 'MEDIUM',
    BIG = 'BIG',
    INSTITUTION = 'INSTITUTION'
}

export const enum CompanyType {
    PRIVATE = 'PRIVATE',
    PUBLIC = 'PUBLIC',
    NO_PROFIT = 'NO_PROFIT',
    ONG = 'ONG',
    SELF_EMPLOYED = 'SELF_EMPLOYED',
    ASSOCIATION = 'ASSOCIATION',
    INSTITUTION = 'INSTITUTION',
    AUTHORITY = 'AUTHORITY',
    AGENCY = 'AGENCY',
    CHARITY = 'CHARITY'
}

export const enum NumberOfEmployees {
    FROM_1_TO_9 = 'FROM_1_TO_9',
    FROM_10_TO_19 = 'FROM_10_TO_19',
    FROM_20_TO_49 = 'FROM_20_TO_49',
    FROM_50_TO_99 = 'FROM_50_TO_99',
    FROM_100_TO_499 = 'FROM_100_TO_499',
    FROM_500_TO_999 = 'FROM_500_TO_999',
    FROM_1000_TO_1499 = 'FROM_1000_TO_1499',
    FROM_1500_TO_1999 = 'FROM_1500_TO_1999',
    FROM_2000_TO_2499 = 'FROM_2000_TO_2499',
    FROM_2500_TO_4999 = 'FROM_2500_TO_4999',
    FROM_5000_TO_9999 = 'FROM_5000_TO_9999',
    MORE_THAN_10000 = 'MORE_THAN_10000'
}

export interface ICompany {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    companyName?: string;
    companyDescription?: any;
    companySize?: CompanySizeType;
    companyType?: CompanyType;
    numberOfEmployee?: NumberOfEmployees;
    streetAddress?: string;
    foundationDate?: Moment;
    vatNumber?: string;
    email?: string;
    phone?: string;
    mobilePhone?: string;
    country?: Country;
    region?: string;
    province?: string;
    city?: string;
    cap?: string;
    urlSite?: string;
    enabled?: boolean;
    userId?: number;
    imageUrl?: string;
    sectorId?: number;
    sectorDescription?: string;
    sectors?: ICompanySector[];
    sponsoringInstitutionId?: number;
    skills?: ICompanySkill[];
    editAvailable?: boolean;
    deleteAvailable?: boolean;
    applicationAvailable?: boolean;
}

export class Company implements ICompany {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public companyName?: string,
        public companyDescription?: any,
        public companySize?: CompanySizeType,
        public companyType?: CompanyType,
        public numberOfEmployee?: NumberOfEmployees,
        public streetAddress?: string,
        public foundationDate?: Moment,
        public vatNumber?: string,
        public email?: string,
        public phone?: string,
        public mobilePhone?: string,
        public country?: Country,
        public region?: string,
        public province?: string,
        public city?: string,
        public cap?: string,
        public urlSite?: string,
        public enabled?: boolean,
        public userId?: number,
        public imageUrl?: string,
        public sectorId?: number,
        public sectorDescription?: string,
        public sectors?: ICompanySector[],
        public sponsoringInstitutionId?: number,
        public skills?: ICompanySkill[],
        public editAvailable?: boolean,
        public deleteAvailable?: boolean,
        public applicationAvailable?: boolean
    ) {
        this.enabled = this.enabled || false;
        this.editAvailable = this.editAvailable || false;
        this.deleteAvailable = this.deleteAvailable || false;
        this.applicationAvailable = this.applicationAvailable || false;
    }
}

export class CompanyInfo implements ICompany {
    constructor(
        public id?: number,
        public companyName?: string,
        public companyDescription?: any,
        public companySize?: CompanySizeType,
        public companyType?: CompanyType,
        public numberOfEmployee?: NumberOfEmployees,
        public streetAddress?: string,
        public foundationDate?: Moment,
        public vatNumber?: string,
        public email?: string,
        public phone?: string,
        public mobilePhone?: string,
        public country?: Country,
        public region?: string,
        public province?: string,
        public city?: string,
        public cap?: string,
        public urlSite?: string,
        public enabled?: boolean,
        public userId?: number,
        public username?: number,
        public imageUrl?: string,
        public sectorId?: number,
        public sectorDescription?: string,
        public sponsoringInstitutionId?: number
    ) {
    }
}

export class CompanySearchFilter {
    constructor(
        public companyName?: string,
        public city?: string,
        public country?: Country,
        public sectorId?: number
    ) {
    }
}
