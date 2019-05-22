/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { CompanySkillDeleteDialogComponent } from 'app/entities/company-skill/company-skill-delete-dialog.component';
import { CompanySkillService } from 'app/entities/company-skill/company-skill.service';

describe('Component Tests', () => {
    describe('CompanySkill Management Delete Component', () => {
        let comp: CompanySkillDeleteDialogComponent;
        let fixture: ComponentFixture<CompanySkillDeleteDialogComponent>;
        let service: CompanySkillService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [CompanySkillDeleteDialogComponent]
            })
                .overrideTemplate(CompanySkillDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompanySkillDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanySkillService);
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
