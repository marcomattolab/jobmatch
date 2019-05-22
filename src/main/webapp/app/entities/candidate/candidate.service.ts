import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, pipeToResponse } from 'app/shared';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FileService } from 'app/shared/file/file.service';
import { ICandidate } from 'app/shared/model/candidate.model';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';
import * as moment from 'moment';
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';

type EntityResponseType = HttpResponse<ICandidate>;
type EntityArrayResponseType = HttpResponse<ICandidate[]>;

@Injectable({ providedIn: 'root' })
export class CandidateService {
    public resourceUrl = SERVER_API_URL + 'api/candidates';

    constructor(protected http: HttpClient, protected fileService: FileService, protected userSessionStorage: UserSessionStorageService) { }

    create(candidate: ICandidate): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(candidate);
        return this.http
            .post<ICandidate>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(candidate: ICandidate): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(candidate);
        return this.http
            .put<ICandidate>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICandidate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findByUser(): Observable<EntityResponseType> {
        return this.http
            .get<ICandidate>(`${this.resourceUrl}/currentUser`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    countAll(): Observable<number> {
        const options = createRequestOption({});
        const countCache = this.userSessionStorage.retrieve('countAllCandidates');
        if (countCache) {
            return of(countCache);
        }
        return pipeToResponse(this.http.get<number>(`${this.resourceUrl}/count`, { params: options, observe: 'response' }))
            .pipe(
                tap((count: number) => this.userSessionStorage.store('countAllCandidates', count)
                )
            );
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICandidate[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    findSuggestedCandidatesByCompanyId(id: number): Observable<ICandidate[]> {
        const suggestedCandidates = this.userSessionStorage.retrieve('suggestedCandidates');
        if (suggestedCandidates) {
            return of(suggestedCandidates);
        }
        return this.http
            .get<ICandidate[]>(`${this.resourceUrl}/suggestedCandidates/${id}`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res).body),
                tap((res: ICandidate[]) => this.userSessionStorage.store('suggestedCandidates', res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(candidate: ICandidate): ICandidate {
        const copy: ICandidate = Object.assign({}, candidate, {
            createdDate: candidate.createdDate != null && candidate.createdDate.isValid() ? candidate.createdDate.toJSON() : null,
            lastModifiedDate:
                candidate.lastModifiedDate != null && candidate.lastModifiedDate.isValid() ? candidate.lastModifiedDate.toJSON() : null,
            birthday: candidate.birthday != null && candidate.birthday.isValid() ? candidate.birthday.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
            res.body.birthday = res.body.birthday != null ? moment(res.body.birthday) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((candidate: ICandidate) => {
                candidate.createdDate = candidate.createdDate != null ? moment(candidate.createdDate) : null;
                candidate.lastModifiedDate = candidate.lastModifiedDate != null ? moment(candidate.lastModifiedDate) : null;
                candidate.birthday = candidate.birthday != null ? moment(candidate.birthday) : null;
            });
        }
        return res;
    }
}
