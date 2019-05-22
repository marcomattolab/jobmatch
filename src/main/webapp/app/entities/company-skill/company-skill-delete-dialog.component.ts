import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanySkill } from 'app/shared/model/company-skill.model';
import { CompanySkillService } from './company-skill.service';

@Component({
    selector: 'jhi-company-skill-delete-dialog',
    templateUrl: './company-skill-delete-dialog.component.html'
})
export class CompanySkillDeleteDialogComponent {
    companySkill: ICompanySkill;

    constructor(
        protected companySkillService: CompanySkillService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companySkillService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'companySkillListModification',
                content: 'Deleted an companySkill'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-skill-delete-popup',
    template: ''
})
export class CompanySkillDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ companySkill }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompanySkillDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.companySkill = companySkill;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/company-skill', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/company-skill', { outlets: { popup: null } }]);
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
