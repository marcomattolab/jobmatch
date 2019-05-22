/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { JobOfferSkillDeleteDialogComponent } from 'app/entities/job-offer-skill/job-offer-skill-delete-dialog.component';
import { JobOfferSkillService } from 'app/entities/job-offer-skill/job-offer-skill.service';

describe('Component Tests', () => {
    describe('JobOfferSkill Management Delete Component', () => {
        let comp: JobOfferSkillDeleteDialogComponent;
        let fixture: ComponentFixture<JobOfferSkillDeleteDialogComponent>;
        let service: JobOfferSkillService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [JobOfferSkillDeleteDialogComponent]
            })
                .overrideTemplate(JobOfferSkillDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobOfferSkillDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobOfferSkillService);
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
