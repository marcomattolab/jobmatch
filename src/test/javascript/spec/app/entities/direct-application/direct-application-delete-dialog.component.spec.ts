/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { DirectApplicationDeleteDialogComponent } from 'app/entities/direct-application/direct-application-delete-dialog.component';
import { DirectApplicationService } from 'app/entities/direct-application/direct-application.service';

describe('Component Tests', () => {
    describe('DirectApplication Management Delete Component', () => {
        let comp: DirectApplicationDeleteDialogComponent;
        let fixture: ComponentFixture<DirectApplicationDeleteDialogComponent>;
        let service: DirectApplicationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [DirectApplicationDeleteDialogComponent]
            })
                .overrideTemplate(DirectApplicationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DirectApplicationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DirectApplicationService);
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
