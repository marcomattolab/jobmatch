/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JobmatchTestModule } from '../../../test.module';
import { SkillTagDeleteDialogComponent } from 'app/entities/skill-tag/skill-tag-delete-dialog.component';
import { SkillTagService } from 'app/entities/skill-tag/skill-tag.service';

describe('Component Tests', () => {
    describe('SkillTag Management Delete Component', () => {
        let comp: SkillTagDeleteDialogComponent;
        let fixture: ComponentFixture<SkillTagDeleteDialogComponent>;
        let service: SkillTagService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [SkillTagDeleteDialogComponent]
            })
                .overrideTemplate(SkillTagDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SkillTagDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillTagService);
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
