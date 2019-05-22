/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CompanyService } from 'app/entities/company/company.service';
import { ICompany, Company, CompanySizeType, CompanyType, NumberOfEmployees } from 'app/shared/model/company.model';
import { Country } from 'app/shared/model/candidate.model';

describe( 'Service Tests', () => {
    describe( 'Company Service', () => {
        let injector: TestBed;
        let service: CompanyService;
        let httpMock: HttpTestingController;
        let elemDefault: ICompany;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule( {
                imports: [HttpClientTestingModule]
            } );
            injector = getTestBed();
            service = injector.get( CompanyService );
            httpMock = injector.get( HttpTestingController );
            currentDate = moment();

            elemDefault = new Company(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                CompanySizeType.SMALL,
                CompanyType.PRIVATE,
                NumberOfEmployees.FROM_1_TO_9,
                'AAAAAAA',
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
        } );

        describe( 'Service methods', async () => {
            it( 'should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format( DATE_TIME_FORMAT ),
                        lastModifiedDate: currentDate.format( DATE_TIME_FORMAT ),
                        foundationDate: currentDate.format( DATE_FORMAT )
                    },
                    elemDefault
                );
                service
                    .find( 123 )
                    .pipe( take( 1 ) )
                    .subscribe( resp => expect( resp ).toMatchObject( { body: elemDefault } ) );

                const req = httpMock.expectOne( { method: 'GET' } );
                req.flush( JSON.stringify( returnedFromService ) );
            } );

            it( 'should create a Company', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdDate: currentDate.format( DATE_TIME_FORMAT ),
                        lastModifiedDate: currentDate.format( DATE_TIME_FORMAT ),
                        foundationDate: currentDate.format( DATE_FORMAT )
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        foundationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create( new Company( null ) )
                    .pipe( take( 1 ) )
                    .subscribe( resp => expect( resp ).toMatchObject( { body: expected } ) );
                const req = httpMock.expectOne( { method: 'POST' } );
                req.flush( JSON.stringify( returnedFromService ) );
            } );

            it( 'should update a Company', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format( DATE_TIME_FORMAT ),
                        lastModifiedDate: currentDate.format( DATE_TIME_FORMAT ),
                        companyName: 'BBBBBB',
                        companyDescription: 'BBBBBB',
                        companySize: 'BBBBBB',
                        companyType: 'BBBBBB',
                        numberOfEmployee: 'BBBBBB',
                        streetAddress: 'BBBBBB',
                        picture: 'BBBBBB',
                        foundationDate: currentDate.format( DATE_FORMAT ),
                        vatNumber: 'BBBBBB',
                        email: 'BBBBBB',
                        phone: 'BBBBBB',
                        mobilePhone: 'BBBBBB',
                        country: 'BBBBBB',
                        region: 'BBBBBB',
                        province: 'BBBBBB',
                        city: 'BBBBBB',
                        cap: 'BBBBBB',
                        urlSite: 'BBBBBB',
                        enabled: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        foundationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update( expected )
                    .pipe( take( 1 ) )
                    .subscribe( resp => expect( resp ).toMatchObject( { body: expected } ) );
                const req = httpMock.expectOne( { method: 'PUT' } );
                req.flush( JSON.stringify( returnedFromService ) );
            } );

            it( 'should return a list of Company', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format( DATE_TIME_FORMAT ),
                        lastModifiedDate: currentDate.format( DATE_TIME_FORMAT ),
                        companyName: 'BBBBBB',
                        companyDescription: 'BBBBBB',
                        companySize: 'BBBBBB',
                        companyType: 'BBBBBB',
                        numberOfEmployee: 'BBBBBB',
                        streetAddress: 'BBBBBB',
                        picture: 'BBBBBB',
                        foundationDate: currentDate.format( DATE_FORMAT ),
                        vatNumber: 'BBBBBB',
                        email: 'BBBBBB',
                        phone: 'BBBBBB',
                        mobilePhone: 'BBBBBB',
                        country: 'BBBBBB',
                        region: 'BBBBBB',
                        province: 'BBBBBB',
                        city: 'BBBBBB',
                        cap: 'BBBBBB',
                        urlSite: 'BBBBBB',
                        enabled: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        foundationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query( expected )
                    .pipe(
                    take( 1 ),
                    map( resp => resp.body )
                    )
                    .subscribe( body => expect( body ).toContainEqual( expected ) );
                const req = httpMock.expectOne( { method: 'GET' } );
                req.flush( JSON.stringify( [returnedFromService] ) );
                httpMock.verify();
            } );

            it( 'should delete a Company', async () => {
                const rxPromise = service.delete( 123 ).subscribe( resp => expect( resp.ok ) );

                const req = httpMock.expectOne( { method: 'DELETE' } );
                req.flush( { status: 200 } );
            } );
        } );

        afterEach(() => {
            httpMock.verify();
        } );
    } );
} );
