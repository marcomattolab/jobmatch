/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { JobOfferUpdateComponent } from 'app/entities/job-offer/job-offer-update.component';
import { JobOfferService } from 'app/entities/job-offer/job-offer.service';
import { JobOffer } from 'app/shared/model/job-offer.model';

describe('Component Tests', () => {
    describe('JobOffer Management Update Component', () => {
        let comp: JobOfferUpdateComponent;
        let fixture: ComponentFixture<JobOfferUpdateComponent>;
        let service: JobOfferService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [JobOfferUpdateComponent]
            })
                .overrideTemplate(JobOfferUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JobOfferUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobOfferService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JobOffer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobOffer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JobOffer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobOffer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
