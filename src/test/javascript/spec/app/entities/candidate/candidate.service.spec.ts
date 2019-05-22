/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { ICandidate, Candidate, GenderType, Country } from 'app/shared/model/candidate.model';

describe('Service Tests', () => {
    describe('Candidate Service', () => {
        let injector: TestBed;
        let service: CandidateService;
        let httpMock: HttpTestingController;
        let elemDefault: ICandidate;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CandidateService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Candidate(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                GenderType.MALE,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                Country.IT,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        birthday: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Candidate', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        birthday: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        birthday: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Candidate(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Candidate', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        lastName: 'BBBBBB',
                        firstName: 'BBBBBB',
                        gender: 'BBBBBB',
                        birthday: currentDate.format(DATE_FORMAT),
                        streetAddress: 'BBBBBB',
                        picture: 'BBBBBB',
                        email: 'BBBBBB',
                        phone: 'BBBBBB',
                        mobilePhone: 'BBBBBB',
                        country: 'BBBBBB',
                        region: 'BBBBBB',
                        province: 'BBBBBB',
                        city: 'BBBBBB',
                        cap: 'BBBBBB',
                        cv: 'BBBBBB',
                        note: 'BBBBBB',
                        enabled: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        birthday: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Candidate', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        lastName: 'BBBBBB',
                        firstName: 'BBBBBB',
                        gender: 'BBBBBB',
                        birthday: currentDate.format(DATE_FORMAT),
                        streetAddress: 'BBBBBB',
                        picture: 'BBBBBB',
                        email: 'BBBBBB',
                        phone: 'BBBBBB',
                        mobilePhone: 'BBBBBB',
                        country: 'BBBBBB',
                        region: 'BBBBBB',
                        province: 'BBBBBB',
                        city: 'BBBBBB',
                        cap: 'BBBBBB',
                        cv: 'BBBBBB',
                        note: 'BBBBBB',
                        enabled: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        birthday: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Candidate', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
