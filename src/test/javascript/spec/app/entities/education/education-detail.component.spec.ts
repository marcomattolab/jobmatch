/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { EducationDetailComponent } from 'app/entities/education/education-detail.component';
import { Education } from 'app/shared/model/education.model';

describe('Component Tests', () => {
    describe('Education Management Detail Component', () => {
        let comp: EducationDetailComponent;
        let fixture: ComponentFixture<EducationDetailComponent>;
        const route = ({ data: of({ education: new Education(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [EducationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EducationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EducationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.education).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
