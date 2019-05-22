/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { CompanySectorDeleteDialogComponent } from 'app/entities/company-sector/company-sector-delete-dialog.component';
import { CompanySectorService } from 'app/entities/company-sector/company-sector.service';

describe('Component Tests', () => {
    describe('CompanySector Management Delete Component', () => {
        let comp: CompanySectorDeleteDialogComponent;
        let fixture: ComponentFixture<CompanySectorDeleteDialogComponent>;
        let service: CompanySectorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [CompanySectorDeleteDialogComponent]
            })
                .overrideTemplate(CompanySectorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompanySectorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanySectorService);
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
