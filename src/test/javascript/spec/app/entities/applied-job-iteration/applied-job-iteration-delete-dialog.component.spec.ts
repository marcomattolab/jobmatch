/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { AppliedJobIterationDeleteDialogComponent } from 'app/entities/applied-job-iteration/applied-job-iteration-delete-dialog.component';
import { AppliedJobIterationService } from 'app/entities/applied-job-iteration/applied-job-iteration.service';

describe('Component Tests', () => {
    describe('AppliedJobIteration Management Delete Component', () => {
        let comp: AppliedJobIterationDeleteDialogComponent;
        let fixture: ComponentFixture<AppliedJobIterationDeleteDialogComponent>;
        let service: AppliedJobIterationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [AppliedJobIterationDeleteDialogComponent]
            })
                .overrideTemplate(AppliedJobIterationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AppliedJobIterationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppliedJobIterationService);
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
