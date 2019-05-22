import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import * as moment from 'moment';

export interface IUser {
    id?: any;
    login?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    activated?: boolean;
    langKey?: string;
    authorities?: any[];
    createdBy?: string;
    createdDate?: Date;
    lastModifiedBy?: string;
    lastModifiedDate?: Date;
    password?: string;
    currentRoleId?: number;
}

export class User implements IUser {
    constructor(
        public id?: any,
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public activated?: boolean,
        public langKey?: string,
        public authorities?: any[],
        public createdBy?: string,
        public createdDate?: Date,
        public lastModifiedBy?: string,
        public lastModifiedDate?: Date,
        public password?: string,
        public currentRoleId?: number
    ) {
        this.id = id ? id : null;
        this.login = login ? login : null;
        this.firstName = firstName ? firstName : null;
        this.lastName = lastName ? lastName : null;
        this.email = email ? email : null;
        this.activated = activated ? activated : false;
        this.langKey = langKey ? langKey : null;
        this.authorities = authorities ? authorities : null;
        this.createdBy = createdBy ? createdBy : null;
        this.createdDate = createdDate ? createdDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.password = password ? password : null;
    }
}

export interface IUser {
    id?: any;
    login?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    activated?: boolean;
    langKey?: string;
    authorities?: any[];
    createdBy?: string;
    createdDate?: Date;
    lastModifiedBy?: string;
    lastModifiedDate?: Date;
    password?: string;
    currentRoleId?: number;
}

export class RegistrationUser {
    constructor(
        public id?: any,
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public langKey?: string,
        public roleAccount?: string,
        public password?: string,
    ) { }
}

export class RegistrationCandidateUser extends RegistrationUser {
    constructor(
        public id?: any,
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public langKey?: string,
        public password?: string,
    ) {
        super(id, login, firstName, lastName, email, langKey, AuthoritiesConstants.ROLE_CANDIDATE, password);
    }
 }

export class RegistrationCompanyUser extends RegistrationUser {
    constructor(
        public id?: any,
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public langKey?: string,
        public password?: string,
        public companyName?: string,
        public foundationDate?: moment.Moment,
        public companyDescription?: string,
        public country?: string,
        public vatNumber?: string,
        public phone?: string,
        public sectorId?: number,
        public companyType?: string,
        public companySizeType?: string,
        public numberOfEmpoyee?: string
    ) {
        super(id, login, firstName, lastName, email, langKey, AuthoritiesConstants.ROLE_COMPANY, password);
    }
}

export class RegistrationSponsoringInstitutionUser extends RegistrationUser {
    constructor(
        public id?: any,
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public langKey?: string,
        public password?: string,
        public istituitionName?: string,
        public foundationDate?: moment.Moment,
        public istituitionDescription?: string,
        public country?: string,
        public vatNumber?: string,
        public phone?: string
    ) {
        super(id, login, firstName, lastName, email, langKey, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION, password);
    }
}
