/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { JobOfferDeleteDialogComponent } from 'app/entities/job-offer/job-offer-delete-dialog.component';
import { JobOfferService } from 'app/entities/job-offer/job-offer.service';

describe('Component Tests', () => {
    describe('JobOffer Management Delete Component', () => {
        let comp: JobOfferDeleteDialogComponent;
        let fixture: ComponentFixture<JobOfferDeleteDialogComponent>;
        let service: JobOfferService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [JobOfferDeleteDialogComponent]
            })
                .overrideTemplate(JobOfferDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobOfferDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobOfferService);
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
