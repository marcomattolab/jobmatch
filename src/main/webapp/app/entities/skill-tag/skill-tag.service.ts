import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkillTag } from 'app/shared/model/skill-tag.model';

type EntityResponseType = HttpResponse<ISkillTag>;
type EntityArrayResponseType = HttpResponse<ISkillTag[]>;

@Injectable({ providedIn: 'root' })
export class SkillTagService {
    public resourceUrl = SERVER_API_URL + 'api/skill-tags';

    constructor(protected http: HttpClient) {}

    create(skillTag: ISkillTag): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(skillTag);
        return this.http
            .post<ISkillTag>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(skillTag: ISkillTag): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(skillTag);
        return this.http
            .put<ISkillTag>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISkillTag>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISkillTag[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(skillTag: ISkillTag): ISkillTag {
        const copy: ISkillTag = Object.assign({}, skillTag, {
            createdDate: skillTag.createdDate != null && skillTag.createdDate.isValid() ? skillTag.createdDate.toJSON() : null,
            lastModifiedDate:
                skillTag.lastModifiedDate != null && skillTag.lastModifiedDate.isValid() ? skillTag.lastModifiedDate.toJSON() : null
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
            res.body.forEach((skillTag: ISkillTag) => {
                skillTag.createdDate = skillTag.createdDate != null ? moment(skillTag.createdDate) : null;
                skillTag.lastModifiedDate = skillTag.lastModifiedDate != null ? moment(skillTag.lastModifiedDate) : null;
            });
        }
        return res;
    }
}
