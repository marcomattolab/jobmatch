/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { AppliedJobDetailComponent } from 'app/entities/applied-job/applied-job-detail.component';
import { AppliedJob } from 'app/shared/model/applied-job.model';

describe('Component Tests', () => {
    describe('AppliedJob Management Detail Component', () => {
        let comp: AppliedJobDetailComponent;
        let fixture: ComponentFixture<AppliedJobDetailComponent>;
        const route = ({ data: of({ appliedJob: new AppliedJob(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [AppliedJobDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AppliedJobDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AppliedJobDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.appliedJob).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
