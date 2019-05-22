import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompanySkill } from 'app/shared/model/company-skill.model';

type EntityResponseType = HttpResponse<ICompanySkill>;
type EntityArrayResponseType = HttpResponse<ICompanySkill[]>;

@Injectable({ providedIn: 'root' })
export class CompanySkillService {
    public resourceUrl = SERVER_API_URL + 'api/company-skills';

    constructor(protected http: HttpClient) {}

    create(companySkill: ICompanySkill): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(companySkill);
        return this.http
            .post<ICompanySkill>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(companySkill: ICompanySkill): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(companySkill);
        return this.http
            .put<ICompanySkill>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICompanySkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICompanySkill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(companySkill: ICompanySkill): ICompanySkill {
        const copy: ICompanySkill = Object.assign({}, companySkill, {
            createdDate: companySkill.createdDate != null && companySkill.createdDate.isValid() ? companySkill.createdDate.toJSON() : null,
            lastModifiedDate:
                companySkill.lastModifiedDate != null && companySkill.lastModifiedDate.isValid()
                    ? companySkill.lastModifiedDate.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((companySkill: ICompanySkill) => {
                companySkill.createdDate = companySkill.createdDate != null ? moment(companySkill.createdDate) : null;
                companySkill.lastModifiedDate = companySkill.lastModifiedDate != null ? moment(companySkill.lastModifiedDate) : null;
            });
        }
        return res;
    }
}
