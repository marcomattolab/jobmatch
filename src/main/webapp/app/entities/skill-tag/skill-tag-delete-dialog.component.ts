import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkillTag } from 'app/shared/model/skill-tag.model';
import { SkillTagService } from './skill-tag.service';

@Component({
    selector: 'jhi-skill-tag-delete-dialog',
    templateUrl: './skill-tag-delete-dialog.component.html'
})
export class SkillTagDeleteDialogComponent {
    skillTag: ISkillTag;

    constructor(protected skillTagService: SkillTagService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.skillTagService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'skillTagListModification',
                content: 'Deleted an skillTag'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-skill-tag-delete-popup',
    template: ''
})
export class SkillTagDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ skillTag }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SkillTagDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.skillTag = skillTag;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/skill-tag', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/skill-tag', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
