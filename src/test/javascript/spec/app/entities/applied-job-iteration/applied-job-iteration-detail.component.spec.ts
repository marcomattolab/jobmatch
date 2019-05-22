/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { AppliedJobIterationDetailComponent } from 'app/entities/applied-job-iteration/applied-job-iteration-detail.component';
import { AppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';

describe('Component Tests', () => {
    describe('AppliedJobIteration Management Detail Component', () => {
        let comp: AppliedJobIterationDetailComponent;
        let fixture: ComponentFixture<AppliedJobIterationDetailComponent>;
        const route = ({ data: of({ appliedJobIteration: new AppliedJobIteration(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [AppliedJobIterationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AppliedJobIterationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AppliedJobIterationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.appliedJobIteration).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
