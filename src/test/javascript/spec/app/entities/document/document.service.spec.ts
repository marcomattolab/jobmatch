/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DocumentService } from 'app/entities/document/document.service';
import { IDocument, Document, DocumentType } from 'app/shared/model/document.model';

describe('Service Tests', () => {
    describe('Document Service', () => {
        let injector: TestBed;
        let service: DocumentService;
        let httpMock: HttpTestingController;
        let elemDefault: IDocument;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DocumentService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Document(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                DocumentType.CV,
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Document', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Document(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Document', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        documentType: 'BBBBBB',
                        name: 'BBBBBB',
                        content: 'BBBBBB',
                        description: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate
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

            it('should return a list of Document', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        documentType: 'BBBBBB',
                        name: 'BBBBBB',
                        content: 'BBBBBB',
                        description: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate
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

            it('should delete a Document', async () => {
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
