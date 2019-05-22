/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { JobExperienceDeleteDialogComponent } from 'app/entities/job-experience/job-experience-delete-dialog.component';
import { JobExperienceService } from 'app/entities/job-experience/job-experience.service';

describe('Component Tests', () => {
    describe('JobExperience Management Delete Component', () => {
        let comp: JobExperienceDeleteDialogComponent;
        let fixture: ComponentFixture<JobExperienceDeleteDialogComponent>;
        let service: JobExperienceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [JobExperienceDeleteDialogComponent]
            })
                .overrideTemplate(JobExperienceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobExperienceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobExperienceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
