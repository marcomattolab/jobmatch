/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { AppliedJobDeleteDialogComponent } from 'app/entities/applied-job/applied-job-delete-dialog.component';
import { AppliedJobService } from 'app/entities/applied-job/applied-job.service';

describe('Component Tests', () => {
    describe('AppliedJob Management Delete Component', () => {
        let comp: AppliedJobDeleteDialogComponent;
        let fixture: ComponentFixture<AppliedJobDeleteDialogComponent>;
        let service: AppliedJobService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [AppliedJobDeleteDialogComponent]
            })
                .overrideTemplate(AppliedJobDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AppliedJobDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppliedJobService);
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
