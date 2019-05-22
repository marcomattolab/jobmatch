/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { SponsoringInstitutionDetailComponent } from 'app/entities/sponsoring-institution/sponsoring-institution-detail.component';
import { SponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';

describe('Component Tests', () => {
    describe('SponsoringInstitution Management Detail Component', () => {
        let comp: SponsoringInstitutionDetailComponent;
        let fixture: ComponentFixture<SponsoringInstitutionDetailComponent>;
        const route = ({ data: of({ sponsoringInstitution: new SponsoringInstitution(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [SponsoringInstitutionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SponsoringInstitutionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SponsoringInstitutionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sponsoringInstitution).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
